package example

import org.apache.spark.sql.SparkSession

object HelloWorld {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.enableHiveSupport().getOrCreate()
    val tableName = "sample.message"

    val count = spark.read.table(tableName).count()

    spark.sql(s"insert into $tableName values (${count + 1}, 'hello world!')")

    spark.read.table(tableName).show(false)
  }
}
