require 'fileutils'
list=[]
dir=Dir.pwd
IO.foreach("../sdk/docs/tree.html") do |line|
  n=line.index("javadoc/com/")
  next unless n
  p=line.index(".",n)
  part="#{line[n+7..p]}java"
  name="#{dir}/rare/core#{part}"
  list << name if File.exists?(name)
  name="#{dir}/rare/swingx#{part}"
  list << name if File.exists?(name)
end 
h={}
list.each do |file|
  h[file]=file
end
elist=[]
Dir.glob("#{dir}/rare/core/**/*.java").each do |name|
  next if name unless h[name]
  next if name.index("/widget/")
  next if name.index("/viewer/")
  elist << name unless h[name]
end
Dir.glob("#{dir}/rare/swingx/**/*.java").each do |name|
  elist << name unless h[name]
end
puts "INPUT = #{list.join(" ")}"
puts "EXCLUDE = #{elist.join(" ")}"
