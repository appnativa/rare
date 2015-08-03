@echo off
java -cp ..\..\libs\spotcompiler.jar;..\..\libs\appnativa.util.jar;..\..\libs\appnativa.spot.jar com.sparseware.spot.SPOTNode rare_objects_work.spot > rare_objects.spot
copy rare_objects.spot ..\..\..\projects\eclipse\studio\src\com\appnativa\studio\rare_objects.spot
