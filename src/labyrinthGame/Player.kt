package labyrinthGame

import java.awt.Color
import javax.swing.JPanel

class Player : JPanel() {
    internal var x: Int = 0
    internal var y: Int = 0

    init {
        this.background = Color.RED
        this.setSize(Labyrinth.panelSize, Labyrinth.panelSize)
    }

    fun moveLeft() {
        if (x > 0 && (Labyrinth.map[x - 1][y] == 0 || Labyrinth.map[x - 1][y] == 9)) {
            this.setLocation(this.getX() - 25, this.getY())
            x--
        }
    }

    fun moveRight() {
        if (x < Labyrinth.columns - 1 && (Labyrinth.map[x + 1][y] == 0 || Labyrinth.map[x + 1][y] == 9)) {
            this.setLocation(this.getX() + 25, this.getY())
            x++
        }
    }

    fun moveUp() {
        if (y > 0 && (Labyrinth.map[x][y - 1] == 0 || Labyrinth.map[x][y - 1] == 9)) {
            this.setLocation(this.getX(), this.getY() - 25)
            y--
        }
    }

    fun moveDown() {
        if (y < Labyrinth.rows - 1 && (Labyrinth.map[x][y + 1] == 0 || Labyrinth.map[x][y + 1] == 9)) {
            this.setLocation(this.getX(), this.getY() + 25)
            y++
        }
    }
}

class Exit : JPanel() {

    init {
        this.background = Color.ORANGE
        this.setSize(Labyrinth.panelSize, Labyrinth.panelSize)
    }
}