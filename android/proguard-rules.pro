# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


####################################################################################################
##### ANDROID APPCOMPAT LIBRARY                                                                #####
####################################################################################################

#-keep class androidx.appcompat.widget.** { *; }

#-keep class com.google.android.material.** { *; }

#-keepclassmembers class androidx.lifecycle.** { *; }
#-keep class androidx.lifecycle.* { *; }
#-dontwarn androidx.lifecycle.*

#-keep class androidx.** { *; }
#-keep interface androidx.** { *; }

####################################################################################################
##### THREE TEN                                                                                #####
####################################################################################################

#-dontwarn java.util.spi.LocaleServiceProvider
#-dontwarn sun.util.calendar.*
#-keep class org.threeten.bp.zone.*
#-keep class org.threeten.bp.*
#-keepclassmembers class org.threeten.bp.** { *; }


####################################################################################################
##### GOOGLE PLAY SERVICES                                                                     #####
####################################################################################################

#-keep class * extends java.util.ListResourceBundle {
#    protected Object[][] getContents();
#}

#-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
#    public static final *** NULL;
#}

#-keepnames @com.google.android.gms.common.annotation.KeepName class *
#-keepclassmembernames class * {
#    @com.google.android.gms.common.annotation.KeepName *;
#}

#-keepnames class * implements android.os.Parcelable {
#    public static final ** CREATOR;
#}

####################################################################################################
##### STETHO                                                                                   #####
####################################################################################################

# Updated as of Stetho 1.1.1
#
# Note: Doesn't include Javascript console lines.
# See https://github.com/facebook/stetho/tree/master/stetho-js-rhino#proguard
#-keep class com.facebook.stetho.** { *; }

####################################################################################################
##### MATERIALPROGRESSBAR                                                                      #####
####################################################################################################

#-keep class me.zhanghai.android.materialprogressbar.** { *; }

####################################################################################################
##### OKHTTP3                                                                                  #####
####################################################################################################

-keepattributes Signature
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

####################################################################################################
##### OKIO                                                                                     #####
####################################################################################################

-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

####################################################################################################
##### RETROFIT                                                                                 #####
####################################################################################################

# Retrofit 2.X
## https://square.github.io/retrofit/ ##

# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep inherited services.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface * extends <1>

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# R8 full mode strips generic signatures from return types if not kept.
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>

# With R8 full mode generic signatures are stripped for classes that are not kept.
-keep,allowobfuscation,allowshrinking class retrofit2.Response

####################################################################################################
##### CALLIGRAPHY                                                                              #####
####################################################################################################

#-keep class uk.co.chrisjenx.calligraphy.* { *; }
#-keep class uk.co.chrisjenx.calligraphy.*$* { *; }

####################################################################################################
##### ICONICS                                                                                  #####
####################################################################################################

-keep class .R
-keep class **.R$* {
    <fields>;
}

####################################################################################################
##### CRASHLYTICS                                                                              #####
####################################################################################################

-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

#-keep class com.crashlytics.** { *; }
#-keep class com.crashlytics.android.**
#-keepattributes SourceFile, LineNumberTable, *Annotation*

# If you are using custom exceptions, add this line so that custom exception types are skipped during obfuscation:
#-keep public class * extends java.lang.Exception

# For Fabric to properly de-obfuscate your crash reports, you need to remove this line from your ProGuard config:
# -printmapping mapping.txt

####################################################################################################
##### RXJAVA                                                                                   #####
####################################################################################################

#-keep class rx.schedulers.Schedulers {
#    public static <methods>;
#}
#-keep class rx.schedulers.ImmediateScheduler {
#    public <methods>;
#}
#-keep class rx.schedulers.TestScheduler {
#    public <methods>;
#}
#-keep class rx.schedulers.Schedulers {
#    public static ** test();
#}
#-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
#    long producerIndex;
#    long consumerIndex;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
#    long producerNode;
#    long consumerNode;
#}

####################################################################################################
##### GSON                                                                                     #####
####################################################################################################

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
#-keepattributes Signature

# For using GSON @Expose annotation
#-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


####################################################################################################
##### BOTTOMBAR                                                                                #####
####################################################################################################

-dontwarn com.roughike.bottombar.**

####################################################################################################
##### PARCELER                                                                                 #####
####################################################################################################

-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }

####################################################################################################
##### RX BINDING                                                                               #####
####################################################################################################

-dontwarn com.jakewharton.rxbinding2.**

####################################################################################################
##### kotlinx.coroutines                                                                       #####
####################################################################################################

-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}


####################################################################################################
##### GLIDE                                                                                    #####
####################################################################################################

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

####################################################################################################
##### MOCKITO                                                                                  #####
####################################################################################################

-dontwarn org.mockito.**


####################################################################################################
##### MISC                                                                                  #####
####################################################################################################

-dontwarn java.lang.management.**
-dontwarn kotlinx.atomicfu.AtomicBoolean
-dontwarn kotlinx.atomicfu.AtomicFU
-dontwarn org.mockito.**
-dontwarn sun.reflect.**
-dontwarn android.test.**

####################################################################################################
##### CUSTOM RULES                                                                       #####
####################################################################################################

-keepattributes *Annotation*
-keepattributes Signature

-keep enum eu.bazarsearch.app.** { *; }

# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
   static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
   static **$* *;
}
-keepclassmembers class <2>$<3> {
   kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
   public static ** INSTANCE;
}
-keepclassmembers class <1> {
   public static <1> INSTANCE;
   kotlinx.serialization.KSerializer serializer(...);
}

-keep class * {
 @kotlinx.serialization.SerialName <fields>;
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault


-dontwarn com.google.api.client.http.**
-dontwarn org.joda.time.**

-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn org.slf4j.impl.StaticMDCBinder
-dontwarn org.slf4j.impl.StaticMarkerBinder

-keep class org.koin.** { *; }
-keep class org.koin.core.** { *; }
-keep class org.koin.dsl.** { *; }

-keep interface org.koin.core.** { *; }
-keepclassmembers class * { public <init>(...); }

# Ktor
-keep class io.ktor.** { *; }
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.atomicfu.**
-dontwarn io.netty.**
-dontwarn com.typesafe.**
-dontwarn org.slf4j.**

-dontwarn coil3.PlatformContext
-dontwarn io.ktor.client.network.sockets.SocketTimeoutException
-dontwarn io.ktor.client.network.sockets.TimeoutExceptionsCommonKt
-dontwarn io.ktor.client.plugins.HttpTimeout$HttpTimeoutCapabilityConfiguration
-dontwarn io.ktor.client.plugins.HttpTimeout$Plugin
-dontwarn io.ktor.client.plugins.HttpTimeout
-dontwarn io.ktor.util.InternalAPI
-dontwarn io.ktor.utils.io.ByteReadChannelJVMKt
-dontwarn io.ktor.utils.io.CoroutinesKt
-dontwarn io.ktor.utils.io.DelimitedKt
-dontwarn io.ktor.utils.io.LookAheadSession
-dontwarn io.ktor.utils.io.ReadSessionKt
-dontwarn io.ktor.utils.io.bits.Memory
-dontwarn io.ktor.utils.io.core.Buffer$Companion
-dontwarn io.ktor.utils.io.core.Buffer
-dontwarn io.ktor.utils.io.core.ByteBuffersKt
-dontwarn io.ktor.utils.io.core.BytePacketBuilder
-dontwarn io.ktor.utils.io.core.ByteReadPacket$Companion
-dontwarn io.ktor.utils.io.core.ByteReadPacket
-dontwarn io.ktor.utils.io.core.CloseableJVMKt
-dontwarn io.ktor.utils.io.core.Input
-dontwarn io.ktor.utils.io.core.InputArraysKt
-dontwarn io.ktor.utils.io.core.InputPrimitivesKt
-dontwarn io.ktor.utils.io.core.Output
-dontwarn io.ktor.utils.io.core.OutputPrimitivesKt
-dontwarn io.ktor.utils.io.core.PreviewKt
-dontwarn io.ktor.utils.io.core.internal.ChunkBuffer
-dontwarn io.ktor.utils.io.streams.InputKt