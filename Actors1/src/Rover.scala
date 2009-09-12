import scala.actors._

object Heading extends Enumeration {
	type Heading = Value
	val North, South, East, West = Value
}

object Rover extends Actor{
	var heading : Heading = Heading.North

	def act() { 
	loop {      
		receive {
		case North => heading = North
		case South => heading = South
		case East => heading = East
		case West => heading = West
		case x => println(x)
		}
	}
}  
}


class Div(divisor: Int) extends Actor {
	var sum =  0	

	def act() {
		loop {
			receive {
			case "getSum" => reply("return", sum)
			case x : Int =>
			  if ( x % divisor == 0)
			  sum += x 
			}
		}
	}
}

class Filter(filters : List[Div]) extends Actor {
	def act() {
	  var total = 0
		loop {
			receive {
			case "printTotal" => println(total)
			case (s: String, sum : Int) => total += sum
			case "total" => filters.foreach { theDiv => theDiv ! "getSum" }
			case x : Int => filters.foreach { theDiv => theDiv ! x }
			}
		}
	}
}


val div3 = new Div(3)
div3.start
val div5 = new Div(5)
div5.start
  

val filter = new Filter(List(div3, div5))
1.to(999).foreach { x => filter ! x  }