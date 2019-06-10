package labyrinthGame

import labyrinthGame.Labyrinth.Companion.columns
import labyrinthGame.Labyrinth.Companion.rows
import java.awt.Color
import java.awt.Graphics
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.*
import javax.swing.JFrame
import javax.swing.SwingUtilities


class View private constructor() : JFrame() {

    /**
     * Conventions:
     *
     * maze[row][col][]
     *
     * Values: 0 = not-visited node
     * 1 = wall (blocked)
     * 2 = visited node
     * 9 = target node
     *
     * borders must be filled with "1" to void ArrayIndexOutOfBounds exception.
     */
    private val mazes = Array(columns + 2) { IntArray(rows + 2) }
    private val path = ArrayList<Int>()

    init {
        title = "Simple Maze Solver"
        setSize( 540,  540)
        setLocationRelativeTo(null)


        for (row in 0 until mazes.size) {
            for (col in 0 until mazes[0].size) {
                mazes[row][col] = 1
            }
        }

        for (row in maze.indices) {
            for (col in 0 until maze[0].size) {
                    mazes[row + 1][col + 1] = maze[col][row]
            }
        }

        MazeAutoSolve.searchPath(mazes, 1, 1, path)

    }

    override fun paint(g: Graphics?) {
        super.paint(g)

        g!!.translate(50, 50)
        val fil = 20

        // draw the maze
        for (row in mazes.indices) {
            for (col in 0 until mazes[0].size) {
                val color: Color = when (mazes[row][col]) {
                    1 -> Color.BLACK
                    9 -> Color.RED
                    else -> Color.GREEN
                }
                g.color = color
                g.fillRect(fil * col, fil * row, fil, fil)
                g.color = Color.BLACK
                g.drawRect(fil* col, fil * row, fil, fil)
            }
        }

        // draw the path list
        var p = 0
        while (p < path.size) {
            val pathX = path[p]
            val pathY = path[p + 1]
            g.color = Color.RED
            g.fillRect(pathX * fil, pathY *fil, fil, fil)
            p += 2
        }

    }

    companion object {
        internal var maze = Array(columns) { IntArray(rows) }


        @JvmStatic
        fun main(args: Array<String>?) {
            SwingUtilities.invokeLater {
                val view = View()
                view.isVisible = true
            }
        }
    }

}




class MazeAutoSolve {

    companion object {

        fun searchPath(maze: Array<IntArray>, x: Int, y: Int, path: MutableList<Int>): Boolean {

            if (maze[y][x] == 9) {
                path.add(x)
                path.add(y)
                return true
            }

            if (maze[y][x] == 0) {
                maze[y][x] = 2

                var dx = -1
                var dy = 0
                if (searchPath(maze, x + dx, y + dy, path)) {
                    path.add(x)
                    path.add(y)
                    return true
                }

                dx = 1
                dy = 0
                if (searchPath(maze, x + dx, y + dy, path)) {
                    path.add(x)
                    path.add(y)
                    return true
                }

                dx = 0
                dy = -1
                if (searchPath(maze, x + dx, y + dy, path)) {
                    path.add(x)
                    path.add(y)
                    return true
                }

                dx = 0
                dy = 1
                if (searchPath(maze, x + dx, y + dy, path)) {
                    path.add(x)
                    path.add(y)
                    return true
                }
            }
            return false
        }
    }
}