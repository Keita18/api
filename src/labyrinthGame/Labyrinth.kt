package labyrinthGame

import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.BufferedReader
import java.io.FileReader
import java.util.*
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel

class Labyrinth internal constructor(str: String) : JFrame() {
    private var p: Player = Player()
    private var exit: Exit = Exit()

    init {
        loadMap(str)
        this.isResizable = false
        this.setSize(columns * panelSize + 50, rows * panelSize + 120)
        this.title = "Maze"
        this.layout = null

        this.addKeyListener(object : KeyListener {

            override fun keyPressed(e: KeyEvent) {
                val key = e.keyCode

                revalidate()
                repaint()

                //Player movement
                if (key == KeyEvent.VK_W) {
                    p.moveUp()
                }
                if (key == KeyEvent.VK_A) {
                    p.moveLeft()
                }
                if (key == KeyEvent.VK_S) {
                    p.moveDown()
                }
                if (key == KeyEvent.VK_D) {
                    p.moveRight()
                }
                if (key == KeyEvent.VK_R) {
                    View.maze = map
                    View.main(null)
                }

                if (p.x == endX && p.y == endY) {

                    JOptionPane.showMessageDialog(
                        null,
                        "Congratulations, you've beaten the level!",
                        "End Game",
                        JOptionPane.INFORMATION_MESSAGE
                    )
                    dispose()
                    App()
                }
            }

            override fun keyReleased(arg0: KeyEvent) {
                // TODO Auto-generated method stub

            }

            override fun keyTyped(arg0: KeyEvent) {
                // TODO Auto-generated method stub

            }

        })

        this.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                //System.out.println((columns*panelSize)+50+"-"+((rows*panelSize)+70));
                System.exit(0)
            }
        })

        this.setLocationRelativeTo(null)
        drawMaze()
        //System.out.println(Arrays.deepToString(map))
        this.isVisible = true
    }

    private fun drawMaze(){
        //Create player
        p = Player()
        p.isVisible = true
        this.add(p)

        exit = Exit()
        exit.isVisible = true
        this.add(exit)

        p.setLocation(0 * panelSize + 23, 0 * panelSize + 25)
        p.y = 0
        //Color map
        for (y in 0 until columns) {
            for (x in 0 until rows) {
                val tile = JPanel()
                tile.setSize(panelSize, panelSize)
                tile.setLocation(x * panelSize + 23, y * panelSize + 25)

                when {
                    map[x][y] == 1 -> tile.background = Color.BLACK
                    map[x][y] == 9 -> {
                        tile.background = Color.ORANGE
                        exit.setLocation(x* panelSize + 23,  y* panelSize + 25)
                        endX = x
                        endY = y
                    }
                    else -> tile.background = Color.GREEN
                }

                tile.isVisible = true
                this.add(tile)
            }
        }
    }

    private fun loadMap(str: String) {
        try {
            val br = BufferedReader(FileReader(str))
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
                            print(mapChar)
                        }
                        counter++
                        x++
                    }
                }
            }
        } catch (e: Exception) {
            println("Unable to load existing map(if exists), creating new map.")
        }
    }

    companion object {
        internal var rows = 20
        internal var columns = 20
        internal var panelSize = 25
        internal var map = Array(columns) { IntArray(rows) }

        internal var endX: Int = 0
        internal var endY: Int = 0

        @JvmStatic
        fun main(args: Array<String>) {
            App()
        }
    }
}