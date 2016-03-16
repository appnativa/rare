require 'fileutils'
COMPONENT_SUBS=[
"apple-canvas",
"apple-carousel",
"apple-collapsible",
"apple-colorchooser",
"apple-combobox",
"apple-coreplot",
"apple-datechooser",
"apple-navigator",
"apple-progressbar",
"apple-slider",
"apple-spinner",
"apple-splitpane",
"apple-statusbar",
"apple-tabpane",
"apple-table_and_tree",
"core-canvas",
"core-carousel",
"core-charts",
"core-collapsible",
"core-colorchooser",
"core-combobox",
"core-datechooser",
"core-navigator",
"core-progressbar",
"core-slider",
"core-spinner",
"core-splitpane",
"core-statusbar",
"core-tabpane",
"core-table_and_tree",
"ios-spinner",
"ios-table_and_tree",
"apple-android-htmllabel"
]
class String
  def starts_with?(str)
    return false if !str or str.length>self.length
    return self[0,str.length]==str
  end
  def ends_with?(str)
    return false if !str or str.length>self.length
    return self.index(str,self.length-str.length)!=nil
  end
 ##
  # Replaces one character with another
  #
  # @param what the character to replace
  # @param with the replacement character
  # @return the number of characters replaced
  #
  def replace_char!(what,with)
    return 0 if empty?
    n=0
    i=0
    while (i = self.index(what, i))
      self[i]=with
      i += 1
      n +=1
    end
    return n
  end
end
def pkg_map
  Dir.glob("**/").each do |name|
    name.replace_char!('/', '.')
    name.chop!
    if name.starts_with?("com.jg")
      puts "#{name}:RAREJG"
    elsif name.starts_with?("com.appnativa.util")
      puts "#{name}:RAREUT"
    elsif name.starts_with?("com.appnativa.rare.widget.")
      puts "#{name}RAREWG"
    elsif name.starts_with?("com.appnativa.rare.viewer.")
      puts "#{name}:RAREVR"
    elsif name.starts_with?("com.appnativa.rare.spot")
      puts "#{name}:RARESPOT"
    elsif name.starts_with?("com.appnativa.rare.ui.layout")
      puts "#{name}:RARELO"
    else
      puts "#{name}:RARE" if name.index('.')!=name.rindex('.')
    end
  end
end
  
def map_packages
  f=File.new("raw/pkg.properties","w")
  $stdout=f
  puts "###########################################
# Mappings for the runtime.
# Java package names are mapped to Objective-C
# file prefix names.
###########################################"
  dir=Dir.pwd
  Dir.chdir(dir+'/../rare/core')
  pkg_map
  Dir.chdir(dir+'/../rare/apple')
  pkg_map
  Dir.chdir(dir+'/../rare/ios')
  pkg_map
  Dir.chdir(dir+'/../util/src')
  pkg_map
  Dir.chdir(dir+'/../util/src-json')
  pkg_map
  Dir.chdir(dir+'/../util/src-xml')
  pkg_map
  Dir.chdir(dir+'/../util/src-html')
  pkg_map
  Dir.chdir(dir+'/../util/src-apple-porting')
  pkg_map
  COMPONENT_SUBS.each do |sub|
	  Dir.chdir(dir+'/../rare/'+sub)
	  pkg_map
  end
  
  puts "com.appnativa.spot:";
  f.close
  $stdout=STDOUT
  Dir.chdir(dir)
  FileUtils.cp_r "raw/pkg.properties", "../../sdk/lib/apple/resources/raw/pkg.properties", :remove_destination => true
end
  
def get_rare_source_path(dir,sdk,osx)
  path= "#{dir}/../util/src:#{sdk}/j2objc/mods:#{dir}/../util/src-apple-porting:#{dir}/../util/src-json" 
  path << ":#{dir}/../spot/src"
  path << ":#{dir}/../rare/core"
  path << ":#{dir}/../rare/apple"
  COMPONENT_SUBS.each do |sub|
	  path << ":#{dir}/../rare/#{sub}"
  end
  path << ":#{dir}/../rare/ios" unless osx
  path << ":#{dir}/../rare/osx" if osx
  return path
end

def get_rare_changes(dir,all)
  check_timestamp(".timestamp_rare",all)
  FileUtils.remove_dir("#{dir}/source/rare",true) if all
  FileUtils.remove_dir("#{dir}/source/jgoodies",true) if all
  FileUtils.remove_dir("#{dir}/include/com/appnativa/rare",true) if all
  FileUtils.remove_dir("#{dir}/include/com/appnativa/jgoodies",true) if all
  FileUtils.remove_dir("#{dir}/include/org",true) if all
  FileUtils.remove_dir("#{dir}/include/android",true) if all
  count=0
  f=File.new('files.txt',"w")
  list=`find ../rare/core -newer .timestamp_rare`.split(/\n/)
  list.each do |item| 
    next unless item.ends_with?(".java")
    item.replace_char!('\\', '/')
    item.strip!
    f.write("#{dir}/#{item}\n")
    count+=1
  end
  list=`find ../rare/apple -newer .timestamp_rare`.split(/\n/)
  list.each do |item| 
    next unless item.ends_with?(".java")
    item.replace_char!('\\', '/')
    item.strip!
    f.write("#{dir}/#{item}\n")
    count+=1
  end
  f.close() 
  `touch  .timestamp_rare`  
  if count==0
    puts "no 'rare' files need transpiling"
    return
  end
  puts "#{count} 'rare' file(s) need transpiling"
  sdk="#{dir}/../../sdk"
  cmd="#{sdk}/j2objc/j2objc -use-arc --prefixes raw/pkg.properties --mapping appnativa.mappings"
  cmd << " -d output"
  cmd << " -sourcepath \"#{get_rare_source_path(dir,sdk,false)}\""
  cmd << " `cat -n -u files.txt`"
  system(cmd)
  copy_files(dir,Dir.glob(File.join("output/com/appnativa/rare/**","*.m")),"/rare/",true)
  copy_files(dir,Dir.glob(File.join("output/com/appnativa/rare/**","*.h")),"/rare/",false)
  copy_files(dir,Dir.glob("output/android/**/*.m"),"/android/",true)
  copy_files(dir,Dir.glob("output/android/**/*.h"),"/android/",false)
  copy_files(dir,Dir.glob("output/com/appnativa/jgoodies/**/*.m"),"/jgoodies/",true)
  copy_files(dir,Dir.glob("output/com/appnativa/jgoodies/**/*.h"),"/jgoodies/",false)
  FileUtils.remove_dir("output",true)
  FileUtils.rm_f("files.txt")
end

def get_rare_apple_changes(dir,all,plat)
  check_timestamp(".timestamp_#{plat}",all)
  FileUtils.remove_dir("#{dir}/source_#{plat}",true) if all
  FileUtils.remove_dir("#{dir}/include_#{plat}",true) if all
  count=0
  f=File.new('files.txt',"w")
  list=`find ../rare/#{plat} -newer .timestamp_#{plat}`.split(/\n/)
  list.each do |item| 
    next unless item.ends_with?(".java")
    item.replace_char!('\\', '/')
    item.strip!
    f.write("#{dir}/#{item}\n")
    count+=1
  end
  f.close() 
  `touch  .timestamp_#{plat}`  
  if count==0
    puts "no 'rare_#{plat}' files need transpiling"
    return
  end
  puts "#{count} 'rare_#{plat}' file(s) need transpiling" 
  sdk="#{dir}/../../sdk"
  cmd="#{sdk}/j2objc/j2objc -use-arc --prefixes raw/pkg.properties --mapping appnativa.mappings"
  cmd << " -d output"
  cmd << " -sourcepath \"#{get_rare_source_path(dir,sdk,false)}\""
  cmd << " `cat -n -u files.txt`"
  system(cmd)
  copy_files(dir,Dir.glob(File.join("output/com/appnativa/rare/**","*.m")),"/rare/",true,"_#{plat}")
  copy_files(dir,Dir.glob(File.join("output/com/appnativa/rare/**","*.h")),"/rare/",false,"_#{plat}")
  FileUtils.remove_dir("output",true)
  FileUtils.rm_f("files.txt")
end

def get_optional_component_changes(dir,all,sub)
  check_timestamp(".timestamp_#{sub}",all)
  count=0
  f=File.new('files.txt',"w")
  list=`find ../rare/#{sub} -newer .timestamp_#{sub}`.split(/\n/)
  list.each do |item| 
    next unless item.ends_with?(".java")
    item.replace_char!('\\', '/')
    item.strip!
    f.write("#{dir}/#{item}\n")
    count+=1
  end
  f.close() 
  `touch  .timestamp_#{sub}`  
  if count==0
    puts "no 'rare_#{sub}' files need transpiling"
    return
  end
  puts "#{count} 'rare_#{sub}' file(s) need transpiling" 
  sdk="#{dir}/../../sdk"
  cmd="#{sdk}/j2objc/j2objc -use-arc --prefixes raw/pkg.properties --mapping appnativa.mappings"
  cmd << " -d output"
  cmd << " -sourcepath \"#{get_rare_source_path(dir,sdk,false)}\""
  cmd << " `cat -n -u files.txt`"
  system(cmd)
  copy_files(dir,Dir.glob(File.join("output/com/appnativa/rare/**","*.m")),"/rare/",true,"/#{sub}")
  copy_files(dir,Dir.glob(File.join("output/com/appnativa/rare/**","*.h")),"/rare/",false,"")
  copy_files(dir,Dir.glob("output/org/**/*.m"),"/org/",true)
  copy_files(dir,Dir.glob("output/org/**/*.h"),"/org/",false)
  copy_files(dir,Dir.glob("output/android/**/*.m"),"/android/",true)
  copy_files(dir,Dir.glob("output/android/**/*.h"),"/android/",false)
  FileUtils.remove_dir("output",true)
  FileUtils.rm_f("files.txt")
end
  
def get_util_changes(dir,all)
  check_timestamp(".timestamp_util",all)
  FileUtils.remove_dir("#{dir}/source/util",true) if all
  FileUtils.remove_dir("#{dir}/include/com/appnativa/util",true) if all
  count=0
  list=`find ../util -newer .timestamp_util`.split(/\n/)
  f=File.new('files.txt',"w")
  list.each do |item| 
    next unless item.ends_with?(".java")
    next if item.index("src-other")
    next if item.index("src-html")
    next if item.index("src-xml")
    item.replace_char!('\\', '/')
    item.strip!
    f.write("#{dir}/#{item}\n")
    count+=1
  end
  f.close() 
  `touch  .timestamp_util`  
  if count==0
    puts "no 'util' files need transpiling"
    return
  end
  puts "#{count} 'util' file(s) need transpiling"
  sdk="#{dir}/../../sdk"
  cmd="#{sdk}/j2objc/j2objc -use-arc --prefixes raw/pkg.properties --mapping appnativa.mappings"
  cmd << " -d output"
  cmd << " -sourcepath \"#{dir}/../util/src:#{sdk}/j2objc/mods:#{dir}/../util/src-apple-porting:#{dir}/../util/src-json:\"" 
  cmd << " `cat -n -u files.txt`"
  system(cmd)
  copy_files(dir,Dir.glob(File.join("output/com/appnativa/util/**","*.m")),"/util/",true)
  copy_files(dir,Dir.glob(File.join("output/com/appnativa/util/**","*.h")),"/util/",false)
  FileUtils.remove_dir("output",true)
  FileUtils.rm_f("files.txt")
end
  
def get_spot_changes(dir,all)
  check_timestamp(".timestamp_spot",all)
  FileUtils.remove_dir("#{dir}/source/spot",true) if all
  FileUtils.remove_dir("#{dir}/include/com/appnativa/spot",true) if all
  count=0
  list=`find ../spot -newer .timestamp_spot`.split(/\n/)
  f=File.new('files.txt',"w")
  list.each do |item| 
    next unless item.ends_with?(".java")
    item.replace_char!('\\', '/')
    item.strip!
    f.write("#{dir}/#{item}\n")
    count+=1
  end
  f.close() 
  `touch  .timestamp_spot`  
  if count==0
    puts "no 'spot' files need transpiling"
    return
  end
  puts "#{count} 'spot' file(s) need transpiling"
  sdk="#{dir}/../../sdk"
  cmd="#{sdk}/j2objc/j2objc -use-arc --prefixes raw/pkg.properties --mapping appnativa.mappings"
  cmd << " -d output"
  cmd << " -sourcepath \"#{dir}/../util/src:#{dir}/../util/src-apple-porting:#{dir}/../spot/src\"" #java source
  cmd << " `cat -n -u files.txt`"
  system(cmd)
  copy_files(dir,Dir.glob("output/com/appnativa/spot/*.m"),"/spot/",true)
  copy_files(dir,Dir.glob("output/com/appnativa/spot/*.h"),"/spot/",false)
  FileUtils.remove_dir("output",true)
  FileUtils.rm_f("files.txt")
end
  
def check_timestamp(name,reset)
  if !File.exists?(name)
    f=File.new(name,"w")
    f.close()
    reset=true
  end
 
  `touch -t 7101010000 #{name}` if reset==true
end
  
def copy_files(dir,files, indexs,m,suffix="")
  files.each do |file|
    dfile=file[file.index(indexs)+1..-1]
    if m
      dfile=dfile[5..-1] if(suffix.length>0 && dfile.index("rare")==0)
      dfile="#{dir}/source#{suffix}/#{dfile}";
    else
      dfile="#{dir}/include#{suffix}/#{file[7..-1]}";
    end
    ddir=File.dirname(dfile);
    FileUtils.mkdir_p(ddir) if !File.exists?(ddir)
    FileUtils.cp_r "#{dir}/#{file}", dfile, :remove_destination => true
  end
end
  
#Main Body  
###########################
all=ARGV[1]=="all"
dir=Dir.pwd
sdk="#{dir}/../../sdk"
if !File.exists?(sdk)
  abort "SDK not found!!!"
end
if dir.ends_with?("rareobjc")
  case ARGV[0]
  when "pkg"
    map_packages
  when "util"
    get_util_changes(dir,all)
  when "spot"
    get_spot_changes(dir,all)
  when "rare"
    get_rare_changes(dir,all)
  when "ios"
    get_rare_apple_changes(dir,all,"ios")
  when "osx"
    get_rare_apple_changes(dir,all,"osx")
  when "opt"
    COMPONENT_SUBS.each do |sub|
      get_optional_component_changes(dir,all,sub)
    end
  when "all"
    map_packages
    get_util_changes(dir,all)
    get_spot_changes(dir,all)
    get_rare_changes(dir,all)
    get_rare_apple_changes(dir,all,"ios")
    COMPONENT_SUBS.each do |sub|
      get_optional_component_changes(dir,all,sub)
    end
    FileUtils.rm_f("files.txt")
  end
else
  puts "This program MUST be run form the 'rareobjc' directory!!!"
end

