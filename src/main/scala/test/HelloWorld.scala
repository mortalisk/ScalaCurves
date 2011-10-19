package test

import swing._
import swing.event._
import java.awt.Graphics2D


class MyPanel extends Panel {
	override def paintComponent(g: Graphics2D) {
		g.drawString("hei", 10, 10)
	}
	listenTo(Mouse.clicks)
	reactions += {
		case MouseClicked(src ,point, mod, cliks, pops) => 
			println("Mouser")
		}

}

object HelloWorld extends SimpleSwingApplication {
	def top = new MainFrame {
		title = "Hello, World!"
		contents = new MyPanel {
			preferredSize = new Dimension(300, 300)

		}
	}
}

