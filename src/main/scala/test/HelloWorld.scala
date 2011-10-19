package test

import swing._
import swing.event._
import java.awt.Graphics2D
import java.awt.geom._

class Vector2(val x:Float=0,val y:Float=0) {
	def interpolate(t:Float, other:Vector2) = {
		new Vector2(this.x*(1-t)+other.x*t,this.y*(1-t)+other.y*t)
	}
}

class Bezier(var points:List[Vector2] = Nil) {

	def interpolate(t:Float, l:List[Vector2] = points) : Vector2 = {
		def r = for (p <- 1 to l.size-1) yield l(p-1).interpolate(t,l(p))
		if (r.size > 1) interpolate(t, r.toList)
		else r(0)
	}
}


class MyPanel extends Panel {

	var bezier = new Bezier
	
	override def paintComponent(g: Graphics2D) {
		g.clearRect(0,0,size.width,size.height)
		
		if (bezier.points.size > 2) {
			val path = new Path2D.Float
			path.moveTo(bezier.points(0).x,bezier.points(0).y)
			var f = 0.01f;
			while(f<=1.0) {
				val p = bezier.interpolate(f)
				path.lineTo(p.x,p.y)
				f+=0.01f
			}
			g.draw(path)
		}
		bezier.points.foreach(p => g.draw(new Ellipse2D.Float(p.x, p.y, 2, 2)))
	}
	listenTo(Mouse.clicks)
	reactions += {
		case MouseClicked(src ,point, mod, clicks, pops) => {
			bezier.points = new Vector2(point.x, point.y) :: bezier.points
			repaint()
		}
	}

}

object HelloWorld extends SimpleSwingApplication {
	def top = new MainFrame {
		title = "Bezier!"
		contents = new MyPanel {
			preferredSize = new Dimension(300, 300)

		}
	}
}

