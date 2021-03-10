import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import java.awt.Color; // general colors (as triples of red,green,blue values)
// and predefined colors (Color.RED, Color.GRAY, etc.)
import java.util.Random;

/*

// interface to represent a list of circles
interface ILoColor {
  // constants
  int RADIUS = 30;

  // creates gap between colors
  WorldImage GAP = new CircleImage(RADIUS + 5, OutlineMode.SOLID, new Color(128, 0, 0));

  // returns the length of the list of colors;
  int length();

  // draw colors
  WorldImage drawColors(int length);

  // returns the Color at the input index
  Color getIndex(int index);

  // attaches the input color to the end of the list of colors
  ILoColor attachToEnd(Color last);

  // removes the last color
  ILoColor removeEnd(int index);

  // removes the color at the specified index
  ILoColor removeColor(int index);

  // draws the number of exact and inexact matches according to the input
  // solution
  WorldImage drawMatches(int length,ILoColor solution);

  // draws the number of exact matches
  WorldImage drawExactMatches(ILoColor solution);

  // draws the number of inexact matches
  WorldImage drawInexactMatches(int length,ILoColor solution);

  // gets the number of exact matches
  int getExactMatches(ILoColor solution);

  // helps get the number of exact matches
  int getExactMatchesHelper(Color first, ILoColor rest);

  // gets the number of inexact matches
  int getInexactMatches(int length,ILoColor solution);


  ILoColor getInexactHelp(Color color);


  boolean contains(Color color);

}

// to represent an empty list of strings
class MtLoColor implements ILoColor {
  MtLoColor() {

  }

  // returns the length of this empty list of colors;
  public int length() {
    return 0;
  }

  // draw colors
  public WorldImage drawColors(int length) {
    return new EmptyImage();
  }

  // returns the Color at the input index
  public Color getIndex(int index) {
    throw new IllegalArgumentException("Invalid index: " + index);
  }

  // attaches the input color to the end of the list of colors
  public ILoColor attachToEnd(Color last) {
    return new ConsLoColor(last, new MtLoColor());
  }

  // removes the last color
  public ILoColor removeEnd(int index) {
    return this;
  }

  // removes the color at the specified index
  public ILoColor removeColor(int index) {
    return this;
  }

  // draws the exact and inexact matches according to the input solution
  public WorldImage drawMatches(int length, ILoColor solution) {
    return new EmptyImage();
  }

  // draws the exact matches
  public WorldImage drawExactMatches(ILoColor solution) {
    return new EmptyImage();
  };

  // draws the inexact matches
  public WorldImage drawInexactMatches(int length, ILoColor solution) {
    return new EmptyImage();
  }

  // gets the number of exact matches
  public int getExactMatches(ILoColor solution) {
    return 0;
  }

  // helps gets the number of exact matches
  public int getExactMatchesHelper(Color first, ILoColor rest) {
    return 0;
  };

  // gets the number of inexact matches
  public int getInexactMatches(int length,ILoColor solution) {
    return 0;
  }

  public boolean contains(Color color) {
    return false;
  }
  
  public ILoColor getInexactHelp(Color color) {
    return this;
  }

}

// to represent a list of string
class ConsLoColor implements ILoColor {
  Color first;
  ILoColor rest;

  ConsLoColor(Color first, ILoColor rest) {
    this.first = first;
    this.rest = rest;
  }

  // returns the length of this list of colors;
  public int length() {
    return 1 + this.rest.length();
  }

  // draw colors
  public WorldImage drawColors(int length) {
    return new BesideImage(
        new OverlayImage(new CircleImage(RADIUS, OutlineMode.SOLID, this.first), GAP),
        this.rest.drawColors(length));
  }

  // returns the Color at the input index
  public Color getIndex(int index) {
    if (index >= this.length()) {
      throw new IllegalArgumentException("Invalid index: " + index);
    }
    else if (index == 0) {
      return this.first;
    }
    else {
      return this.rest.getIndex(index - 1);
    }
  }

  // attaches the input color to the end of the list of colors
  public ILoColor attachToEnd(Color last) {
    return new ConsLoColor(this.first, this.rest.attachToEnd(last));
  }

  // removes the last color
  public ILoColor removeEnd(int index) {
    if (index == 0) {
      return new MtLoColor();
    }
    else {
      return new ConsLoColor(this.first, this.rest.removeEnd(index - 1));
    }
  }

  // removes the color at the specified index
  public ILoColor removeColor(int index) {
    if (index == 0) {
      return this.rest;
    }
    else {
      return new ConsLoColor(this.first, this.rest.removeColor(index - 1));
    }
  }

  // draws the exact and inexact matches according to the input solution
  public WorldImage drawMatches(int length,ILoColor solution) {
    return new BesideImage(this.drawExactMatches(solution), this.drawInexactMatches(length,solution));
  }

  // draws the exact matches
  public WorldImage drawExactMatches(ILoColor solution) {
    return new OverlayImage(new TextImage(Integer.toString(this.getExactMatches(solution)), 30, FontStyle.BOLD,
        Color.white), GAP);
  };

  // draws the inexact matches
  public WorldImage drawInexactMatches(int length,ILoColor solution) {
    return new OverlayImage(new TextImage(Integer.toString(this.getInexactMatches(length,solution)), 30, FontStyle.BOLD,
        Color.white), GAP);
  }

  // gets the number of exact matches
  public int getExactMatches(ILoColor solution) {
    return solution.getExactMatchesHelper(this.first, this.rest);
  }

  // helps gets the number of exact matches
  public int getExactMatchesHelper(Color color, ILoColor rest) {
    if (this.first.equals(color)) {
      return 1 + rest.getExactMatches(this.rest);
    }
    else {
      return rest.getExactMatches(this.rest);
    }
  };

  // gets the number of inexact matches
  public int getInexactMatches(int length, ILoColor solution) {
    /*
    if (solution.contains(this.first) && this.rest.contains(first)) {
      return 1+ this.rest.getInexactMatches(length-1, solution.getInexactHelp(this.first)); 
    }
    */
/*
    if (solution.contains(this.first)) {
      return 1+ this.rest.getInexactMatches(length-1, solution.getInexactHelp(this.first));
    }

    if (length == 0) {
      return 0;
    }
    else {
      return this.rest.getInexactMatches(length-1, solution);
    }

  }
  public boolean contains(Color color) {
    return this.first ==color ||
        this.rest.contains(color);
  }

  public ILoColor getInexactHelp(Color color) {
    if (this.first.equals(color)) {
      return this.rest.getInexactHelp(color);
    }
    else {
      return this;
    }
    
  }


}


// interface which represents a list of a list of colors
interface ILoLoColor {
  // constants
  int RADIUS = 30;

  // creates gap between colors
  WorldImage GAP = new CircleImage(RADIUS + 5, OutlineMode.SOLID, new Color(128, 0, 0));

  // draws past guesses
  WorldImage drawColors1(int length,ILoColor solution);
}

// class which represents an empty list of a list of colors
class MtLoLoColor implements ILoLoColor {
  MtLoLoColor() {

  }

  // draws an empty image
  public WorldImage drawColors1(int length,ILoColor solution) {
    return new EmptyImage();
  }
}

// class which represents a non-empty list of a list of colors
class ConsLoLoColor implements ILoLoColor {
  ILoColor first;
  ILoLoColor rest;

  ConsLoLoColor(ILoColor first, ILoLoColor rest) {
    this.first = first;
    this.rest = rest;
  }

  // draws past guesses
  public WorldImage drawColors1(int length,ILoColor solution) {
    return new AboveImage(
        new BesideImage(this.first.drawColors( length), this.first.drawMatches(length,solution)),
        this.rest.drawColors1(length,solution));
  }
}

// our main game class (Mastermind)
class Mastermind extends World {
  boolean duplicatesAllowed;
  int length;
  int numOfGuesses;
  ILoColor availableColors;

  ILoColor guess;
  ILoColor solution;
  ILoLoColor pastGuesses;

  // main constructor
  Mastermind(boolean duplicatesAllowed, int length, int numOfGuesses, ILoColor availableColors) {
    this.duplicatesAllowed = duplicatesAllowed;

    // determines if the length is invalid (<= 0)
    if (length > 0) {
      this.length = length;
    }
    else {
      throw new IllegalArgumentException("Invalid length: " + Integer.toString(length));
    }

    // determines if the number of guesses is invalid (<= 0)
    if (numOfGuesses > 0) {
      this.numOfGuesses = numOfGuesses;
    }
    else {
      throw new IllegalArgumentException(
          "Invalid number of guesses: " + Integer.toString(numOfGuesses));
    }

    // determines if the list of colors is invalid (= 0)
    if (availableColors.length() == 0) {
      throw new IllegalArgumentException(
          "Invalid list of colors: " + Integer.toString(availableColors.length()));
    }
    else {
      this.availableColors = availableColors;
    }

    // determines if the sequence is winnable, with no duplicated allowed
    if (!duplicatesAllowed && length > availableColors.length()) {
      throw new IllegalArgumentException("Not winnable");
    }

    // initialize the guess as an empty list
    this.guess = new MtLoColor();

    // initialize the solution
    this.solution = this.generateSolution(this.length, this.availableColors);

    // initialize the past guesses as an empty list of a list of colors
    this.pastGuesses = new MtLoLoColor();
  }

  // alternative constructor
  Mastermind(boolean duplicatesAllowed, int length, int numOfGuesses, ILoColor availableColors,
      ILoColor guess, ILoColor solution, ILoLoColor pastGuesses) {
    this.duplicatesAllowed = duplicatesAllowed;

    // determines if the length is invalid (<= 0)
    if (length > 0) {
      this.length = length;
    }
    else {
      throw new IllegalArgumentException("Invalid length: " + Integer.toString(length));
    }

    // determines if the number of guesses is invalid (<= 0)
    if (numOfGuesses > 0) {
      this.numOfGuesses = numOfGuesses;
    }
    else {
      throw new IllegalArgumentException(
          "Invalid number of guesses: " + Integer.toString(numOfGuesses));
    }

    // determines if the list of colors is invalid (= 0)
    if (availableColors.length() == 0) {
      throw new IllegalArgumentException(
          "Invalid list of colors: " + Integer.toString(availableColors.length()));
    }
    else {
      this.availableColors = availableColors;
    }

    // determines if the sequence is winnable, with no duplicated allowed
    if (!duplicatesAllowed && length > availableColors.length()) {
      throw new IllegalArgumentException("Not winnable");
    }

    // updated fields
    this.guess = guess;
    this.solution = solution;
    this.pastGuesses = pastGuesses;
  }

  // returns the WorldScene to be shown on each clock tick
  public WorldScene makeScene() {
    int WIDTH = 500;
    int HEIGHT = 500;

    WorldScene scene = new WorldScene(WIDTH, HEIGHT);

    // draw background
    WorldImage background = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID,
        new Color(128, 0, 0));

    // draws available colors
    WorldImage availableImage = this.availableColors.drawColors(1);

    // draws guess
    WorldImage guessImage = this.guess.drawColors(1);

    // draws solution
    WorldImage solutionImage = this.solution.drawColors(1);

    // draws black rectangle covering solution
    WorldImage blackRectangle = new RectangleImage((int) solutionImage.getWidth(),
        (int) solutionImage.getHeight(), OutlineMode.SOLID, Color.black);

    // draws past guesses
    WorldImage pastImage = this.pastGuesses.drawColors1(this.length,this.solution);

    return scene.placeImageXY(
        new AboveAlignImage(AlignModeX.LEFT, guessImage, pastImage, availableImage), 250, 250);
  }

  // handles key inputs
  public World onKeyEvent(String key) {
    // determines if the key pressed is between 1 and 9
    if ("123456789".contains(key) && this.guess.length() < this.length) {
      return createMastermindForGuess(
          this.guess.attachToEnd(this.availableColors.getIndex(Integer.valueOf(key) - 1)));
    }

    // determines if the key pressed is the enter key
    if (key.equals("enter") && this.guess.length() == this.length) {
      return createMastermindForPastGuesses(this.guess);
    }

    // determines if the key pressed is the delete key
    if (key.equals("backspace") && this.guess.length() > 0) {
      return createMastermindForGuess(this.guess.removeEnd(this.guess.length() - 1));
    }

    return this;
  }

  // returns instance of Mastermind with updated guess
  public World createMastermindForGuess(ILoColor guess) {
    return new Mastermind(this.duplicatesAllowed, this.length, this.numOfGuesses,
        this.availableColors, guess, this.solution, this.pastGuesses);
  }

  // returns instance of Mastermind with updated guess and past guesses
  public World createMastermindForPastGuesses(ILoColor guess) {
    return new Mastermind(this.duplicatesAllowed, this.length, this.numOfGuesses,
        this.availableColors, new MtLoColor(), this.solution,
        new ConsLoLoColor(guess, this.pastGuesses));
  }

  // returns the randomly generated solution
  public ILoColor generateSolution(int length, ILoColor availableColors) {
    Random rand = new Random();
    int randIndex = rand.nextInt(availableColors.length());
    Color randColor = availableColors.getIndex(randIndex);

    if (this.duplicatesAllowed) {
      if (length == 1) {
        return new ConsLoColor(randColor, new MtLoColor());
      }

      else {
        return new ConsLoColor(randColor, this.generateSolution(length - 1, availableColors));
      }
    }
    else {
      if (length == 1) {
        return new ConsLoColor(randColor, new MtLoColor());
      }
      else {
        return new ConsLoColor(randColor,
            this.generateSolution(length - 1, availableColors.removeColor(randIndex)));
      }
    }
  }
  
  public WorldEnd worldEnds() {
    if (this.guess == this.solution) {
      return new WorldEnd(true, this.makeEndScene());
    } else {
      return new WorldEnd(false, this.makeScene());
    }
  }
  
  public WorldImage makeEndScene() {
    return scene.placeImageXY(
        new AboveAlignImage(AlignModeX.LEFT, guessImage, pastImage, availableImage), 250, 250);
  }
}

// examples and tests for Mastermind
class ExamplesMasterminds {
  ExamplesMastermind() {
  }

  // constants
  int RADIUS = 30;

  // creates gap between colors
  WorldImage GAP = new CircleImage(RADIUS + 5, OutlineMode.SOLID, new Color(128, 0, 0));

  // examples of list of colors
  ILoColor empty = new MtLoColor();
  ILoColor rgb = new ConsLoColor(Color.red,
      new ConsLoColor(Color.green, new ConsLoColor(Color.blue, empty)));
  ILoColor cpbg = new ConsLoColor(Color.cyan, new ConsLoColor(Color.pink,
      new ConsLoColor(Color.blue, new ConsLoColor(Color.green, empty))));
  ILoColor cpbgr = new ConsLoColor(Color.blue, new ConsLoColor(Color.cyan,
      new ConsLoColor(Color.pink, new ConsLoColor(Color.green, empty))));
  ILoColor brgb = new ConsLoColor(Color.blue, new ConsLoColor(Color.red,
      new ConsLoColor(Color.GRAY, new ConsLoColor(Color.blue, empty))));
  ILoColor cpbg3 = new ConsLoColor(Color.black, new ConsLoColor(Color.blue,
      new ConsLoColor(Color.GRAY, new ConsLoColor(Color.black, empty))));
  ILoColor cpbg4 = new ConsLoColor(Color.black, new ConsLoColor(Color.blue,
      new ConsLoColor(Color.GRAY, new ConsLoColor(Color.blue, empty))));


  ILoColor bgc = new ConsLoColor(Color.blue,
      new ConsLoColor(Color.green, new ConsLoColor(Color.cyan, empty)));
  ILoColor bbc = new ConsLoColor(Color.blue,
      new ConsLoColor(Color.green, new ConsLoColor(Color.cyan, empty)));
  ILoColor longlist = new ConsLoColor(Color.blue, new ConsLoColor(Color.cyan,
      new ConsLoColor(Color.pink, new ConsLoColor(Color.green,
          new ConsLoColor(Color.red, new ConsLoColor(Color.GRAY, new ConsLoColor(Color.black,
              empty)))))));


  // example of draw color
  WorldImage rgbImage = new BesideImage(
      new OverlayImage(new CircleImage(RADIUS, OutlineMode.SOLID, Color.red), GAP),
      new BesideImage(
          new OverlayImage(new CircleImage(RADIUS, OutlineMode.SOLID, Color.green), GAP),
          new BesideImage(
              new OverlayImage(new CircleImage(RADIUS, OutlineMode.SOLID, Color.blue), GAP))));

  // example of attach to end
  ILoColor rg = new ConsLoColor(Color.red, new ConsLoColor(Color.green, empty));
  ILoColor cpb = new ConsLoColor(Color.cyan,
      new ConsLoColor(Color.pink, new ConsLoColor(Color.blue, empty)));

  // testing length method
  boolean testLength(Tester t) {
    return t.checkExpect(this.empty.length(), 0) && t.checkExpect(this.rgb.length(), 3)
        && t.checkExpect(this.cpbg.length(), 4);
  }

  // testing draw color method
  boolean testDrawColors(Tester t) {
    return t.checkExpect(this.empty.drawColors(1), new EmptyImage())
        && t.checkExpect(this.rgb.drawColors(1), this.rgbImage);
  }

  // testing get index method
  boolean testGetIndex(Tester t) {
    return t.checkExpect(this.rgb.getIndex(0), Color.red)
        && t.checkExpect(this.rgb.getIndex(1), Color.green)
        && t.checkExpect(this.rgb.getIndex(2), Color.blue)
        && t.checkExpect(this.cpbg.getIndex(0), Color.cyan)
        && t.checkExpect(this.cpbg.getIndex(1), Color.pink)
        && t.checkExpect(this.cpbg.getIndex(2), Color.blue)
        && t.checkExpect(this.cpbg.getIndex(3), Color.green);
  }

  // testing for exception in get index method
  boolean testForExceptionGetIndex(Tester t) {
    return t.checkException(new IllegalArgumentException("Invalid index: 0"), this.empty,
        "getIndex", 0)
        && t.checkException(new IllegalArgumentException("Invalid index: 1"), this.empty,
            "getIndex", 1)
        && t.checkException(new IllegalArgumentException("Invalid index: 3"), this.rgb, "getIndex",
            3)
        && t.checkException(new IllegalArgumentException("Invalid index: 4"), this.cpbg, "getIndex",
            4);
  }

  // testing attach to end method
  boolean testAttachToEnd(Tester t) {
    return t.checkExpect(this.empty.attachToEnd(Color.red), new ConsLoColor(Color.red, this.empty))
        && t.checkExpect(this.rg.attachToEnd(Color.blue), this.rgb)
        && t.checkExpect(this.cpb.attachToEnd(Color.green), this.cpbg);
  }

  // testing remove end method
  boolean testRemoveEnd(Tester t) {
    return t.checkExpect(this.empty.removeEnd(0), this.empty)
        && t.checkExpect(this.empty.removeEnd(1), this.empty)
        && t.checkExpect(this.rgb.removeEnd(2), this.rg)
        && t.checkExpect(this.cpbg.removeEnd(3), this.cpb);
  }

  // testing remove color method
  boolean testRemoveColor(Tester t) {
    return t.checkExpect(this.empty.removeColor(0), this.empty)
        && t.checkExpect(this.empty.removeColor(1), this.empty)
        && t.checkExpect(this.rgb.removeColor(2), this.rg)
        && t.checkExpect(this.cpbg.removeColor(3), this.cpb);
  }

  // mastermind examples

  // testing for exceptions in the Mastermind constructor
  boolean testForException(Tester t) {
    return t.checkConstructorException(new IllegalArgumentException("Invalid length: 0"),
        "Mastermind", true, 0, 1, this.rgb)
        && t.checkConstructorException(new IllegalArgumentException("Invalid number of guesses: 0"),
            "Mastermind", true, 1, 0, this.rgb)
        && t.checkConstructorException(new IllegalArgumentException("Invalid list of colors: 0"),
            "Mastermind", true, 1, 1, this.empty)
        && t.checkConstructorException(new IllegalArgumentException("Not winnable"), "Mastermind",
            false, 5, 1, this.cpbg);
  }

  // testing make scene method

  // testing on key event method

  // testing create Mastermind for guess method

  // testing generate solution method

  // testing big bang
  boolean testBigBang(Tester t) {
    World w = new Mastermind(true, 4, 10, this.cpbg);
    int worldWidth = 500;
    int worldHeight = 500;
    double tickRate = 0.1;
    return w.bigBang(worldWidth, worldHeight, tickRate);
  }

  boolean testGetInexact(Tester t) {
    return t.checkExpect(cpbg.getInexactMatches(4,brgb), 1) &&
        
        t.checkExpect(cpbg.getInexactMatches(4,cpbg3),1);
  }
  boolean testcontains(Tester t) {
    return t.checkExpect(cpbg.contains(Color.black), false) &&
        t.checkExpect(cpbg.contains(Color.blue), true);
  }
}
/*