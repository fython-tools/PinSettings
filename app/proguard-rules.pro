-dontwarn kotlin.**
-dontobfuscate
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable

-keep class moe.feng.nyanpasu.pinsettings.util.Preferences {*;}

-keepattributes Signature
-keepattributes Exceptions