require 'fileutils'
def get_packages(dir,hash)
  Dir.glob(dir).each do |name|
    next if name.index("/platform/apple")
    next if name.index("/chart/c")
    next unless name.index("/core")
    n=name.index("/com/appnativa/")
    next unless n
    name=name[n+1..-2]
    name.gsub!('/', '.')
    next if name=="com.appnativa"
    hash[name]=name
  end
end

outdir=ARGV[0]
abort "You must specify an existing directory for the generated files" unless outdir && File.exists?(outdir) 
#Main Body  
###########################
hash={}
get_packages("util/**/",hash)
get_packages("spot/**/",hash)
get_packages("rare/**/",hash)
hash.delete("com.appnativa.util.html")
hash.delete("com.appnativa.util.xml")
hash.delete("com.appnativa.jgoodies")
hash.delete("com.appnativa.jgoodies.forms")
hash.delete("com.appnativa.jgoodies.forms.factories")
hash.delete("com.appnativa.jgoodies.forms.layout")
hash.delete("com.appnativa.jgoodies.forms.util")
f=File.new('packages.txt',"w")
$stdout=f
hash.each_key {|k| puts k}  
$stdout=STDOUT
f.close
`javadoc -d #{outdir} @javadoc_config.txt @packages.txt `
FileUtils.rm_f("packages.txt")


