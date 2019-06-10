package labyrinthGame

import java.awt.Color
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.PrintWriter
import java.util.*
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel


class BlocMakerTile(internal var x: Int, internal var y: Int): JPanel(){

     init {

         addMouseListener(object : MouseAdapter() {
             override fun mousePressed(e: MouseEvent?) {
                 if (e!!.button == MouseEvent.BUTTON1) {
                     background = Color.GREEN
                     LabyrinthBlocMaker.map[x][y] = 0
                 }
                 if (e.button == MouseEvent.BUTTON3) {
                     background = Color.ORANGE
                     LabyrinthBlocMaker.map[x][y] = 9
                 }

             }
         })
     }
 }

class LabyrinthBlocMaker: JFrame() { private val panelSize = 25
    private val mapList = ArrayList<String>()
    private var level = 0
    private var levelsExistAlready = false

    init {
        getMapList()
        getLevelChoice()
        if (level != -1) {
            loadMap()
            this.isResizable = false
            this.setSize(columns * panelSize + 50, rows * panelSize + 70)
            this.title = "Maze Map Maker"
            this.layout = null

            this.addWindowListener(object : WindowAdapter() {
                override fun windowClosing(e: WindowEvent?) {
                    saveMap()
                    App()
                }
            })

            this.setLocationRelativeTo(null)

            for (y in 0 until columns) {
                for (x in 0 until rows) {
                    val tile = BlocMakerTile(x, y)
                    tile.setSize(panelSize - 1, panelSize - 1)
                    tile.setLocation(x * panelSize + 23, y * panelSize + 25)
                    if (x == 0 && y == 0)
                        tile.background = Color.WHITE
                     else if (map[x][y] == 1) {
                        tile.background = Color.BLACK
                    } else {
                        tile.background = Color.DARK_GRAY
                    }

                    tile.isVisible = true

                    this.add(tile)
                }
            }
            this.isVisible = true
        } else {
            App()
        }
    }

    private fun getMapList() {
        for (i in 0..98) {
            val map = File("./Level $i.map")
            if (map.exists()) {
                mapList.add("Level $i.map")
                levelsExistAlready = true
            }
        }
    }


    private fun getLevelChoice() {
        if (levelsExistAlready) {
            val maps = arrayOfNulls<String>(99)
            for (i in 0 until mapList.size) {
                maps[i] = mapList[i]
            }
            maps[mapList.size] = "New level"

            val choice = JOptionPane.showInputDialog(
                null,
                "Which level would you like to play?",
                "Maze Level Selector",
                JOptionPane.QUESTION_MESSAGE,
                null,
                maps,
                maps[1]
            ) as String
            println(choice)
            level = if (choice != "New level") {
                Integer.parseInt(choice.replace("Level ", "").replace(".map", ""))
            } else {
                mapList.size
            }
        }
    }
    fun saveMap() {
        try {
            val writer = PrintWriter("Level $level.map", "UTF-8")
            for (y in 0 until columns) {
                for (x in 0 until rows) {
                    writer.print(map[x][y])
                }
                writer.print("\r\n")
            }
            println(writer)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun loadMap() {
        try {
            val br = BufferedReader(FileReader("Level $level.map"))
            val sb = StringBuilder()
            var line: String? = br.readLine()

            while (line != null) {
                sb.append(line)
                sb.append(System.lineSeparator())
                line = br.readLine()
            }
            val mapStr = sb.toString()

            var counter = 0
            for (y in 0 until columns) {
                run {
                    var x = 0
                    while (x < rows) {
                        val mapChar = mapStr.substring(counter, counter + 1)
                        if (mapChar != "\r\n" && mapChar != "\n" && mapChar != "\r") {//If it's a number
                            //System.out.print(mapChar);
                            map[x][y] = Integer.parseInt(mapChar)
                        } else {//If it is a line break
                            x--
                            //System.out.print(mapChar);
                        }
                        counter++
                        x++
                    }
                }
            }
        } catch (e: Exception) {
            println("Unable to load existing map(if exists), creating new map.")
            for (y in 0 until columns) {
                for (x in 0 until rows) {
                    map[x][y] = 1
                }
            }
        }

    }

    companion object {
        private const val rows = 20
        private const val columns = 20
        internal var map = Array(columns) { IntArray(rows) }
    }
}














