require 'fileutils'

def search_directory(hash)
  Dir.glob("**/*.java").each do |name|
    IO.foreach(name) do |line|
      next unless line.index("(\"Rare.") 
      next if line.index("(\"Rare.action.")
      next if line.index("(\"Rare.text.")
      next if line.index("(\"Rare.runtime.")
      next if line.index("(\"Rare.icon.")
      next if line.index("(\"Rare.template.")
      a=line.split(/\"/)
      a.each do |s|
        next unless s.index("Rare.")==0
        hash[s]=s
      end
    end 
  end
end
def makeText(name)
  a=name.split(/\./)
  len=a.length
  for i in 0..len-1
    aa=a[i].split /(?=[A-Z])/
    for n in 0..aa.length-1
      aa[n].downcase!
    end
    a[i]=aa.join(' ')
  end
  if len==2
    return "Default #{a[1]}" if a[i].index("color")
    return "Default #{a[1]} color"
  end
                   b=a;
  s=a[1];
  a.shift
  a.shift
  return "!!Default #{b.join(' ')} for #{s}s" unless a[0]
  return "Default #{a.join(' ')} #{s}" if name.index("Rare.#{s}.")
  return "Boolean indicating whether to #{a.join(' ')}" if a[0].index("show")==0
  return "Default #{a.join(' ')} for #{s}s"
end
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

hash={};
dir=Dir.pwd
Dir.chdir(dir+'/rare/core')
search_directory(hash)
Dir.chdir(dir+'/rare/core-charts')
search_directory(hash)
Dir.chdir(dir+'/rare/apple')
search_directory(hash)
Dir.chdir(dir+'/rare/ios')
search_directory(hash)
Dir.chdir(dir+'/rare/android')
search_directory(hash)
Dir.chdir(dir+'/rare/swingx')
search_directory(hash)
COMPONENT_SUBS.each do |sub|
Dir.chdir(dir+'/rare/'+sub)
  search_directory(hash)
end
Dir.chdir(dir)
puts "<table>"
hash.sort.map do |key,value|
  puts "<tr><td>#{key}</td><td>#{makeText(key)} </td></tr>"
end
puts "</table>"
