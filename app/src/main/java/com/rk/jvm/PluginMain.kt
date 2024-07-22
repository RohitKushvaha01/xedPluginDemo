package com.rk.jvm

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.rk.xedplugin.API
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class PluginMain : API() {
  
  val TAG = "JVMPlugin"
  val PluginPackageName = "com.rk.jvm"
  
  
  //called when plugin instance is created
  override fun onLoad(app: Application?) {/*reate symlinks from your plugins lib folder to xed's bin folder
    allowing you to use them as binaries*/
    
    /*note : getting package names or files whatever app dependent on your plugin is asumed as xed's package name for
    example if try to get your apps private data directory you will get xed's private data directory*/
    
    
    val libs = getPluginLibsDir(app?.applicationContext!!)
    val fileLibs = File(libs)
    val appBin = app.filesDir?.absolutePath + "/bin"
    
    if (fileLibs.exists() && fileLibs.isDirectory) {
      fileLibs.listFiles()?.forEach { file ->
        if (file.name.contains(".so")) {
          val finalFileName = file.name.removeSuffix(".so").removePrefix("lib")
          symlink(file.absolutePath, "$appBin/$finalFileName")
        }
        
      }
    }
    
    
  }
  
  //most of time main activity never calls this method use onResume Instead
  //called when any activity is created
  override fun onActivityCreate(activity: Activity?) {}
  
  //called when any activity is destroyed
  override fun onActivityDestroy(activity: Activity?) {}
  
  //called when any activity is paused
  override fun onActivityPause(activity: Activity?) {
    
  }
  
  //called when any activity is resumed
  override fun onActivityResume(activity: Activity?) {
    if (activity?.javaClass?.name.equals(activity?.packageName + ".MainActivity.MainActivity")) {
      println("main Activity")
    }
  }
  
  
  //======================================================================================================================
  
  private fun symlink(from: String, to: String) {
    if (File(to).exists()) {
      println("symlink already exists")
      return
    }
    val target: Path = Paths.get(from)
    val link: Path = Paths.get(to)
    try {
      Files.createSymbolicLink(link, target)
    } catch (e: Exception) {
      throw RuntimeException(
        "Plugin Error : Unable to create symbolic link fromPath : $from toPath : $to",
        e
      )
    }
  }
  
  private fun getPluginLibsDir(context: Context): String {
    val appInfo: ApplicationInfo = context.packageManager.getApplicationInfo(PluginPackageName, 0)
    return appInfo.sourceDir.removeSuffix("base.apk") + "/lib"
  }
  
  
}