//Project 5B petstore
//Author: Ian Woodcock
//program to create a petstore and simulate it running for 3 days


// Define the Animal hierarchy
abstract class Animal(val name: String) {
  def sleep(): Unit = println(s"$name is sleeping.")
  def eat(): Unit
  def play(): Unit
  def makeNoise(): Unit
}

//class to create cats that extends animal
class Cat(name: String) extends Animal(name) {
  override def eat(): Unit = println(s"$name the Cat is eating.")
  override def play(): Unit = println(s"$name the Cat is chasing a laser.")
  override def makeNoise(): Unit = println(s"$name the Cat says 'Meow'")
}
//class to create dogs that extends animal
class Dog(name: String) extends Animal(name) {
  override def eat(): Unit = println(s"$name the Dog is eating.")
  override def play(): Unit = println(s"$name the Dog is playing fetch.")
  override def makeNoise(): Unit = println(s"$name the Dog says 'Woof'")
}

// Define the Staff hierarchy
abstract class Staff {
  def arrive(): Unit
  def lunch(): Unit
  def leave(): Unit
}

//create a manager that extends the staff class
class Manager(val name: String) extends Staff {
  //method to open the store for the day
  def openStore(animals: List[Animal]): Unit = {
    println(s"$name the Manager arrives and opens the store.")
    animals.foreach(_.makeNoise())
  }
  //method to close the store for the day
  def closeStore(animals: List[Animal]): Unit = {
    println(s"$name the Manager closes the store.")
    animals.foreach(_.sleep())
  }
  //simple arrive, lunch, and leave methods
  override def arrive(): Unit = println(s"$name the Manager arrives.")
  override def lunch(): Unit = println(s"$name the Manager is having lunch.")
  override def leave(): Unit = println(s"$name the Manager leaves.")
}

//create a clerk that extends the staff class
class Clerk(val name: String) extends Staff {
  //method for clerk to feed animals
  def feedAnimals(animals: List[Animal]): Unit = {
    println(s"$name the Clerk feeds the animals.")
    animals.foreach(_.eat())
  }
  //method for clerk to play with animals
  def playAnimals(animals: List[Animal]): Unit = {
    println(s"$name the Clerk plays with the animals.")
    animals.foreach(_.play())
  }
  //method for clerk to have a 20% chance to sell an animal
  def sellAnimals(animals: List[Animal]): List[Animal] = {
    println(s"$name the Clerk attempts to sell animals.")
    animals.map {
      case cat: Cat => if (scala.util.Random.nextDouble() < 0.2) {
        println(s"${cat.name} the Cat has been sold. A new cat is now available.")
        new Cat("New Cat")
        }
        else cat
      case dog: Dog => if (scala.util.Random.nextDouble() < 0.2) {
        println(s"${dog.name} the Dog has been sold. A new dog is now available.")
        new Dog("New Dog")
        }
        else dog
    }
  }
  //simple arrive, lunch, and leave methods
  override def arrive(): Unit = println(s"$name the Clerk arrives.")
  override def lunch(): Unit = println(s"$name the Clerk is having lunch.")
  override def leave(): Unit = println(s"$name the Clerk leaves.")
}

// create the "Clock" (not perfect but honestly wasnt sure what to do)
case class Clock(var day: Int = 1, var hour: Int = 800) {
  def announceHour(): Unit = {
    println(s"\n Hour $hour.")
    
  } 
}

//put everything into a main and simulate the store running for 3 days
object StoreSimulation {
  def main(args: Array[String]): Unit = {
    val animals = List(new Cat("Rex"), new Cat("Small Cat"), new Cat("Bean"), new Dog("Percy"), new Dog("Oliver"), new Dog("Bailey"))
    val manager = new Manager("Ian")
    val clerk = new Clerk("Caden")
    val clock = new Clock()
    
    // Simulate 3 days
    for (_ <- 1 to 3) { 
      clock.hour = 0800
      println(s"Day ${clock.day} starts.")
      clock.announceHour()
      manager.openStore(animals)
      clock.hour = 0900
      clock.announceHour()
      clerk.arrive()
      clerk.feedAnimals(animals)
      clock.hour = 1100
      clock.announceHour()
      clerk.playAnimals(animals)
      clock.hour = 1200
      clock.announceHour()
      manager.lunch()
      clock.hour = 1300
      clock.announceHour()
      clerk.lunch()
      clock.hour = 1600
      clock.announceHour()
      val updatedAnimals = clerk.sellAnimals(animals)
      clock.hour = 1700
      clock.announceHour()
      clerk.leave()
      clock.hour = 1800
      clock.announceHour()
      manager.closeStore(updatedAnimals)
      manager.leave()
      clock.day += 1
      println(s"Day ${clock.day} ends.\n")
    }
  }
}

//rescources used: scala-lang.org, baeldung.com/scala, tutorialspoint.com/scala