package com.example

import com.alibaba.excel.EasyExcel
import com.alibaba.excel.context.AnalysisContext
import com.alibaba.excel.enums.CellDataTypeEnum
import com.alibaba.excel.metadata.CellExtra
import com.alibaba.excel.metadata.data.CellData
import com.alibaba.excel.metadata.data.ReadCellData
import com.alibaba.excel.read.listener.ReadListener
import kotlinx.serialization.Serializable
import java.io.File
import java.util.function.Consumer

/**
 * created by byron at 2023/12/5
 */
class ExcelParser {
    companion object {

        private const val DIR = "/data/webpage_data/excel/"
//        private const val DIR = "/Users/killgravelee/Downloads/"

        fun list(path: String = DIR): List<String> {
            return File(path).list()!!.asList()
                .filter { !it.startsWith("._") && (it.endsWith(".xlsx") || it.endsWith(".xls")) }
        }

        fun parse(path: String = DIR, fileName: String): List<Sheet> {
            if (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls")) {
                throw RuntimeException("只支持 excel 格式")
            }
            val sheets = mutableListOf<Sheet>()
            var thisSheet = mutableListOf<List<String>>()
            EasyExcel.read(path + fileName, object : ReadListener<Any?> {

                override fun invokeHead(headMap: MutableMap<Int, ReadCellData<*>>?, context: AnalysisContext?) {
                    // 读取第一行 head
                    val row = mutableListOf<String>()
                    headMap!!.values.forEach(Consumer { readCellData: ReadCellData<*>? ->
                        cellData(
                            readCellData!!
                        ) {
                            row.add(it)
                        }
                    })
                    thisSheet.add(row)
                }

                override fun invoke(data: Any?, context: AnalysisContext?) {
                    val row = mutableListOf<String>()
                    context!!.readRowHolder().cellMap.forEach { (_, cell) ->
                        if (cell is CellData<*>) {
                            cellData((cell as CellData<*>?)!!) {
                                row.add(it)
                            }
                        } else if (cell is CellExtra) {
                            row.add(cell.text)
                        }
                    }
                    thisSheet.add(row)
                }

                override fun doAfterAllAnalysed(context: AnalysisContext?) {
                    sheets.add(Sheet(name = context!!.readSheetHolder().sheetName, data = thisSheet))
                    thisSheet = mutableListOf()
                }
            }).doReadAll()
            return sheets
        }

        private fun cellData(cellData: CellData<*>, consumer: Consumer<String>) {
            if (cellData.type == CellDataTypeEnum.STRING || cellData.type == CellDataTypeEnum.DIRECT_STRING) {
                consumer.accept(cellData.stringValue)
            } else if (cellData.type == CellDataTypeEnum.NUMBER) {
                consumer.accept(cellData.numberValue.toPlainString())
            } else if (cellData.type == CellDataTypeEnum.BOOLEAN) {
                consumer.accept(cellData.booleanValue.toString())
            }
        }

    }
}

@Serializable
data class Sheet(val name: String, val data: List<List<String>>);

fun main() {
    val lists = ExcelParser.parse(path = "/Users/killgravelee/Downloads/", fileName = "李登权.xlsx")
    lists.forEach { item ->
        println(item)
    }
}