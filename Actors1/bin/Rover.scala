import scala.actors._

case class North

object Rover extends Actor{

	def act() { 

		while(true) {      
			receive {
			case North => println("I said not to go that way....")
			case "South" => println("Total")
			case x => println(x)
			}
		}
	}  
}
