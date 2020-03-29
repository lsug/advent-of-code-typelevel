
# Advent of Code with Cats

## Day1 - Part 1

### Functional Solution

Even though the first part of Day1 problem can be easily solved in one line of code, we purposely break it down into multiple small functions so that we can demonstrate how to apply typeclass to the problem later. We first write a `fuel` function that takes in a mass of the module, divides by three and subtracts two to find the fuel required to launch a module of a given mass. 

```scala
def fuel(mass: Int): Int = mass / 3 - 2
```

In the problem, your puzzle input is a long list of all the modules on your spacecraft. To find the fuel required for each module, you could apply the `fuel` function to each module of the long list by using `map` function. This is what we’ve done by writing `calculateFuels` function. 

```tut
def calculateFuels(masses: List[Int]): List[Int] = masses.map(fuel)
``` 

Next, to sum up the list of fuels, we make use of the sum method available to Iterable like List in our case, and write a sumFuels() function. Finally, we put all of these small functions into `sumOfFuel` function and solve our Part 1’s problem.

```scala
def sumFuels(fuels: List[Int]): Int = fuels.sum
``` 

The solution of Part 1 can be found in [day-1-step-1-abstraction](https://github.com/lsug/advent-of-code-typelevel/tree/day-1-step-1-abstraction) branch. 

### Type Abstraction

Before jumping into typeclass, we thought it’d be good to start by introducing generic function and type. Have a look at the three functions below.

```scala
def returnInt(x : Int): Int = x
def returnLong(x : Long): Long = x
def returnBoolean(x : Boolean): Boolean = x
```


`returnInt` is a function that accepts and returns a value of type `Int` whereas `returnLong` accepts and returns a type `Long` value and finally `returnBoolean`, a `Boolean` type of value.

Although they each return a different **type** of value, they share the same pattern: return the same value that was used as its argument. Since they do the same thing fundamentally, we could write a single function that does that. In other words, we could abstract over this pattern.

````scala
def identity[A](a: A): A = a
````

`identity[A]` function is such a function and is called a generic function. The square bracket with A letter in it is the type parameter, syntax of generic function in Scala. When we are generalizing, we tend to use letter A, B, C by convention.

Now, to return an `Int`, we can pass in an `Int` value to the identity function like this, `identity[Int](10)`. When you run this, the output will be a value of 10 which is the same as running `returnInt(10)`. By the same logic, to return a `true` value, we simply write `identity[Boolean](true)`.

Also, you could have as many generic types, also known as type parameters, in a function as you like. For example, there are three different types in `tuple3[A, B, C]` below

````scala
def tuple3[A, B, C](a: A, b: B, c: C): (A, B, C) = (a, b, c)
````

So, what is a type? You can think of a type as a set of values. For concrete types like `Boolean`, we know it is a set with just two values: true and false.(draw circle) Earlier, we mentioned that A is a generic type which means we don’t know anything about the possible values lie in set A. (draw circle)

You might be thinking why is having a generic type in a function a good thing? Let’s go back to the `identity[A]` function,  remember how it can take in and return an `Int`, `Long` and `Boolean`. Actually, it can do more than that, it can take in and return **any** type of values due to having a generic type. Compared it to `returnInt`, which can only take in and return a concrete `Int` value, `identity[A]` is a lot more flexible and powerful.

In short, by having a generic function, we can reduce code duplication and achieve polymorphism.

In [day-1-step-1-abstraction](https://github.com/lsug/advent-of-code-typelevel/tree/day-1-step-1-abstraction) branch, there is an exercise called Abstraction. Please try to make every function generic and see if you can pass all the tests.

### Type Class and Type Class Instance

We’ve seen both type and generic function, let’s go into typeclass. Before bombarding you with lots of definitions of what a typeclass is, we thought it would be good to give you a break and go back to the spacecraft. Remember the `fuel` function, let’s try to rewrite the function and make it generic.

```tut
def fuel[A](mass: A): A = mass / 3 - 2
```

If you do that, you’ll notice that the function does not compile and the error message is
 
````text
value / is not a member of type parameter A
````

What the error message is saying is the compiler cannot find `/`, division operation under type `A`. Previously, when the mass parameter was a concrete type `Int`, the function compiled because `/` is defined under type `Int`. Actually, it’s not only value `/` undefined, `-` (minus), `3` and `2` are also undefined under type A.

To make the generic `fuel[A]` function compile, first, we need to define all of them and we do that in an interface, `trait Mass[A]`.

```scala
trait Mass[A] {
    def quotient(a: A, b: A): A
    def minus(a: A, b: A): A
    def two: A
    def three: A
  }
```
> *(quotient is the division of two numbers but discarding the fractional part.*) 

Now, you have the missing components in `Mass[A]`. Next, we need to provide a way for `fuel[A]` to access these components and we do that by adding another parameter to `fuel[A]` function.

```tut
def fuel[A](mass: A)(M: Mass[A]): A = ???
```
Please try and implement the function above before reading on. 

The implementation of `fuel[A]` will look something like this and it will compile. 

```tut
def fuel[A](mass: A)(M: Mass[A]): A = {
    M.minus(M.quotient(mass, M.three), M.two)
  }
``` 

Job done? Not quite. Says, if you were to find the fuel for a mass of 12, how would you do that? How about this `fuel[Int](12)`? If you run that, you’ll get an error message.

```text
error: missing argument list for method fuel...
```

Remember earlier we added another parameter of type `Mass[A]` to `fuel[A]` and because we are replacing type `A` with type `Int`, we need to provide a value of type `Mass[Int]` to the call. However, where do we find that value from? It turns out we need to do a bit of DIY and construct a value of type `Mass[Int]` ourselves! We use the `new` keyword and replace generic type `A` with the type we care about, in our case, type `Int` to construct a concrete value of `Mass`. Finally, we implement all the functions in `Mass` for type `Int`.

```tut
val intHasMass: Mass[Int] = new Mass[Int] {
      def quotient(a: Int, b: Int): Int = a / b
      def minus(a: Int, b: Int): Int = a - b
      def two: Int = 2
      def three: Int = 3
    }
``` 

Now, if you run `fuel[Int](12)(intHasMass)`, you should get a value of 2. There you have it, you have just learned to implement and use a typeclass, `Mass` and a typeclass instance, `intHasMass` to solve the problem.

#### Implicits
Earlier when we tried to run `fuel[Int](12)`, we received the missing parameter error message. Actually, there is a way to make this call work by simply adding an `implicit` keyword to the parameter list of `fuel[A]` and `intHasMass`.

```tut
def fuel[A](mass: A)(implicit M: Mass[A]): A = ...
implicit val intHasMass: Mass[Int] = new Mass[Int] {..}
```

By doing this, the compiler will look for a value with type `Mass[Int]` and pass in the implicit value every time we make a call to `fuel[Int]`. Because we put the implicit typeclass instance in an object, we need to bring it into scope by importing it so that compiler can find the value. 

The codes of this section can be found in [day-1-step-2](https://github.com/lsug/advent-of-code-typelevel/blob/day-1-step-2/src/main/scala/advent/solutions/Day1.scala) branch. 

### Monoid
