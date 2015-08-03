# Real-time Application Rendering Engine
This is the source repository for the [appNativa&reg;](http:///www.appnativa.com) Real-time Application Rendering Engine&trade; (RARE) and associated SDK.


RARE takes what is great about browser based applications, and what is great about native applications and brings them together in a way that leaves behind the drawbacks of either approach. This allows you to have rich, powerful, and 100% native applications that can: keep up with the rapid evolution of hardware, device capabilities, and software technology; live on device or in the cloud; be centrally customized, managed, and maintained; and are easy to build.

##License
The source is licensed under GPLv3. See the [LICENSE](LICENSE) file for more information.  See the [LICENSE-THIRDPARTY](LICENSE-THIRDPARTY) file for information on third party source code utilized by RARE.

##Binaries
You can download precompiled binaries at [http://www.appnativa.com/downloads/](http://www.appnativa.com/downloads/). These binaries are made available under an Apache license. You should familiarize yourself with working with the SDK prior to attempting to build it yourself.

##Demos, Documentation & Support
You can find demos, documentation and support information here [http://www.appnativa.com](http://www.appnativa.com)

You can find the real-world **BellaVista** clinical client application on GitHub at [https://github.com/sparseware/ccp-bellavista](https://github.com/sparseware/ccp-bellavista). Screen shots for that application are available at [https://github.com/sparseware/ccp-bellavista/wiki/Screenshots](https://github.com/sparseware/ccp-bellavista/wiki/Screenshots).

##Development Requirements
* Eclipse v4+
* JDK 8+
* Xcode 6+
* Knowledge of importing and working with eclipse projects
* Knowledge of using Xcode to build Objective-C applications
* Knowledge of using the pre-built SDK binaries to build/test/run applications


##Geting Started
###Importing Projects
####Eclipse
Add a path variable named **APPNATIVA_SDK** with the value of `<installation_directory>/source` to the environment.

This can be done via the Eclipse preferences "*General->Workspace->Linked Resources*" option.


Use the import existing projects into workspace option and choose the *eclipse* sub-directory under the *projects* directory of the installation.
To be able to build the android runtime you need create a hard link named '*res*' under:
`<installation_directory>/projects/eclipse/rare.android`

that points to:
`<installation_directory>/source/rare/resources/all/com/appnativa/rare/resources`.
You can also just create the *res* directory and copy the contents of *resources* directory there.

####Xcode
Use Xcode to open the project located in:
`<installation_directory>/projects/xcode/RareTOUCH/RareTOUCH.xcodeproj`

####Making Changes
The quickest way to get started making changes is by also downloading the pre-built SDK and modifying the demo applications to use the source projects instead of the pre-built binaries.

#####iOS Library
If you make changes to a java file that is part of the iOS library, you need that java source file to be converted to an Objective-C file. The `<installation_directory>/source/rareobjc` directory has a shell script called transpile which will perform this conversion for you.  Use as follows:

* `transpile all` to convert all java files that were modified since the last time the converter was ran.
* `transpile all all` to convert all java files that are part of the iOS library. 

See the *transpile.rb* ruby file for more details.

See the [J2ObjC project](http://j2objc.org/docs/Writing-Native-Methods.html) for how to embed Objective-C source inside of a Java file.

If you add a new java file to the eclipse *rare.ios* project, you also need to add the corresponding Objective-C file to the Xcode project as well.