package labyrinthGame

import java.io.File
import java.util.*
import javax.swing.*

class App {

    private var Menu = JFrame("Maze")
    private var start = JButton("Play")
    private var exit = JButton("Exit")
    private var mapMaker = JButton("Map Maker")
    private var mapList = ArrayList<String>()
    private var lvlList: JComboBox<String>
    private var menuWidth = 100 //Width of each button/item on display
    private var menuHeight = 30//Height of each button/item on display
    private var menuY = 460 //Button/item location on display
    private var width = 490
    private var height = 530

    init {
        //Load map list
        getMapList()
        lvlList = JComboBox(mapList.toTypedArray())

        //Menu Variables
        Menu.isResizable = false
        Menu.setSize(width, height)
        Menu.layout = null
        Menu.setLocationRelativeTo(null)
        Menu.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        //Start Button Variables
        start.setSize(menuWidth, menuHeight)
        start.setLocation(10, menuY)
        Menu.add(start)
        start.addActionListener {
            Labyrinth(Objects.requireNonNull(lvlList.selectedItem).toString())
            Menu.isVisible = false
        }

        //Map Maker Button Variables
        mapMaker.setSize(menuWidth, menuHeight)
        mapMaker.setLocation(120, menuY)
        Menu.add(mapMaker)
        mapMaker.addActionListener {
            LabyrinthBlocMaker()
            Menu.isVisible = false
        }

        //Level Selector
        lvlList.setSize(menuWidth + 35, menuHeight)
        lvlList.setLocation(230, menuY)
        Menu.add(lvlList)

        //Exit Button Variables
        exit.setSize(menuWidth, menuHeight)
        exit.setLocation(375, menuY)
        Menu.add(exit)
        exit.addActionListener { System.exit(0) }

        //Display Menu
        Menu.isVisible = true
    }

    private fun getMapList() {
        for (i in 0..98) {
            val map = File("./Level $i.map")
            if (map.exists()) {
                println("Level $i exists")
                mapList.add("Level $i.map")
            }
        }
    }

}