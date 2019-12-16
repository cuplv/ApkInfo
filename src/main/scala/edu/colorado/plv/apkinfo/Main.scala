import java.io.{File, PrintWriter}
import java.nio.file.Files

import brut.androlib.ApkDecoder
import brut.androlib.res.data.ResTable

import scala.xml.{Elem, XML}

object Main{
  case class MainOptions(apkFile: File, outFile : String = "")
  def main(args: Array[String]): Unit ={
    val parser = new scopt.OptionParser[MainOptions]("ApkInfo") {
      opt[File]('f', "apk-file").required() action { (x, c) => c.copy(apkFile = x)} text "Apk File to get info from."
      opt[String]('o', "out-file").required() action { (x,c) => c.copy(outFile = x)} text "File to write info"
    }
    parser.parse(args,MainOptions(null)) match {
      case Some(opt) => {
        val pkg = mainPackageFromApk(opt.apkFile)
        val pw = new PrintWriter(new File(opt.outFile))
        pw.write(pkg)
        pw.close()
      }
      case None => throw new RuntimeException("Error parsing arguments")
    }
  }
  def mainPackageFromApk(apkFile: File): String ={
    val decoder = new ApkDecoder()
    val tmpdir = new File(Files.createTempDirectory("extract_apk").toUri)
    val outdir = new File(tmpdir.getAbsolutePath + "/out/")
    decoder.setOutDir(outdir)
    decoder.setApkFile(apkFile)
    decoder.decode()
    val table: ResTable = decoder.getResTable()
    val manifestFile: Elem = XML.load(outdir.getAbsolutePath + "/" + "AndroidManifest.xml")
    val manifestPackage = (manifestFile \\ "manifest") \@ "package"
    manifestPackage
  }
}