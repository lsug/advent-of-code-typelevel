# Advent of Code with the Typelevel Stack

![Scala CI](https://github.com/lsug/advent-of-code-typelevel/workflows/Scala%20CI/badge.svg)

# Aim

The [Advent Of Code](https://adventofcode.com/2019/about) is a series of programming challenges

> Advent of Code is an Advent calendar of small programming puzzles for a variety of skill sets and skill levels that can be solved in any programming language you like. People use them as a speed contest, interview prep, company training, university coursework, practice problems, or to challenge each other.
>
> You don't need a computer science background to participate - just a little programming knowledge and some problem solving skills will get you pretty far. Nor do you need a fancy computer; every problem has a solution that completes in at most 15 seconds on ten-year-old hardware.
 -- Eric Wastl, Advent of Code author

This workshop solves the Advent of Code 2019 puzzles using libraries in the [Typelevel ecosystem](https://typelevel.org/projects/).  We'll explore how Typelevel libraries play well together to give clean functional solutions.

# Getting started

1. Open a terminal and clone this repository

   ```sh
   $ git clone https://github.com/lsug/advent-of-code-typelevel.git
   ```

2. Compile the project with [SBT](https://www.scala-sbt.org/)

   ```sh
   $ cd advent-of-code-typelevel
   [advent-of-code-typelevel]$ sbt
   sbt:Advent Of Code> test:compile
   ```

   This will take a while, so you may as well peruse the first Advent of Code challenge https://adventofcode.com/2019/day/1

3. Run the tests

   All of them should fail.  You'll need to complete the Advent of Code challenges to get them to pass.
