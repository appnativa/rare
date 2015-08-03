@echo off
call spot2xsd rare_objects.spot com.appnativa.rare.spot=rare_objects >rare_objects.xsd
call spotcompile rare_objects.spot -html "Rare Objects" >rare_objects.html
call spotcompile rare_objects.spot -htmlindex "rare_objects.html">rare_objects_index.html
call spotcompile rare_objects.spot -syntax >rare_objects.syn
copy rare_objects.xsd \rare\schema
copy rare_objects.xsd \www\htdocs\rare\schema

