Feature: Direction Blinking Indicator

  The pitman arm is used for controlling the direction blinking indicator. This feature is for defining positions available by the pitman arm for indicating through blinking.

    Background:
      Given the Ignition is on

     Rule: Direction blinking is only available when ignition is on.

    @requirement(ELS-1,ELS-5)
    Scenario: Driver signals for turning right
      When the driver moves the pitman arm in the upward position
      Then the vehicle flashes all right indicators synchronously

    @requirement(ELS-1,ELS-5)
    Scenario: Driver signals for turning left
      When the driver moves the pitman arm in the downward position
      Then the vehicle flashes all left indicators synchronously

   @requirement(ELS-1,ELS-5)
   Scenario: Driver turns on ignition when the pitman arm is up
     Given ignition is off
     And pitman arm is up
     When the driver turns on the ignition
     Then the right indicator are blinking

   Scenario: Driver changes signalling from turning left to right
     Given the ignition is on
     And the left indicator is blinking
     When the driver moves the pitman arm in the upward position
     Then the vehicle flashes all right indicators synchronously

   @requirement(ELS-1,ELS-5)
   Scenario: Driver inserts key without turning on ignition with pitman arm up
     Given ignition is off
     And pitman arm is up
     When the driver inserts the key without turning on ignition
     Then the right indicator will not blink
    Rule: Tip-Blinking is only available when ignition is on

    @requirement(ELS-2,ELS-5)
    Scenario: Driver signals warning ahead on right side
      When the driver moves the pitman arm in the tip-blinking upward position for less than 0.5 seconds (500 ms)
      Then all right indicators should flash for 3 flashing cycles

    @requirement(ELS-2,ELS-5)
    Scenario: Driver signals warning ahead on left side
      When the driver moves the pitman arm in the tip-blinking downward position for less than 0.5 seconds (500 ms)
      Then all left indicators should flash for 3 flashing cycles

    @requirement(ELS-1,ELS-2,ELS-5)
      Scenario: Driver changes signalling warning ahead on right to turning left
      Given the ignition is on
      And there is a tip-blinking cycle occuring on the right direction indicator
      When the driver moves the pitman arm in the downward position
      Then the vehicle flashes all left indicators synchronously

    @requirement(ELS-2,ELS-5)
      Scenario: Driver stops ignition while signalling warning on right
      Given the ignition is on
      And there is a tip-blinking cycle occurring on the right direction indicator
      When the driver turns the ignition off
      Then the vehicle stops the flashing cycles

    @requirement(ELS-2,ELS-5)
      Scenario: Driver signals warning on right and turns on ignition
      Given the ignition is off
      When the driver moves the pitman arm in the tip-blinking upward position for less than 0.5 seconds (500 ms)
      And the driver turns on the ignition
      Then the right indicator will not blink

    Rule: Engage Pitman arm in another direction or engaging hazard switch will stop tip-blinking flashing cycle

    @requirement(ELS-3,ELS-5)
    Scenario: Driver changes signalling warning from right to left
      When the driver moves the pitman arm from upward to downward position during the 3 flashing cycles of tip-blinking
      Then the right indicator tip-blinking will stop
      And the requesting left indicator flashing cycle will start

    @requirement(ELS-3,ELS-5)
    Scenario: Driver engages pitman arm in an upward position during tip-blinking flash cycle
      When the pitman arm is moved in an upward position from downward during the 3 flashing cycles of tip-blinking
      Then the left indicator tip-blinking will stop
      And the requesting right indicator flashing cycle will start

    @requirement(ELS-3,ELS-5)
    Scenario: Driver engages hazard warning switch while signalling warning ahead
      When the driver engages the hazard warning switch during the 3 flashing cycles of tip-blinking
      Then the tip-blinking will stop
      And the vehicle flashes all left indicators synchronously
      And the vehicle flashes all right indicators synchronously

    @requirement(ELS-3,ELS-5)
    Scenario: Driver

    Rule: If the pitman arm is held for more than 0.5 seconds in a tip-blinking position then direction indicators cycles are released until pitman arm leaves the position

    @requirement(ELS-4,ELS-5)
    Scenario: Engage pitman arm for more than 0.5 seconds (500 ms) in tip-blinking left
      When the pitman arm is held for more than 0.5 seconds in tip-blinking left
      Then flash-cycles are released for all direction indicators on the left until the pitman arm leaves tip-blinking left

    @requirement(ELS-4,ELS-5)
    Scenario: Engage pitman arm for more than 0.5 seconds (500 ms) in tip-blinking right
      When the pitman arm is held for more than 0.5 seconds in tip-blinking right
      Then flash-cycles are released for all direction indicators on the right until the pitman arm leaves tip-blinking right

    Rule: Cars sold in the USA or Canada will have daytime running light dimmed by 50% during direction blinking

    @requirement(ELS-6)
    Scenario: Engage direction blinking by car sold in the USA
      When direction blinking is engaged on the blinking side by a car sold in the USA
      Then the daytime running light must be dimmed by 50%

    @requirement(ELS-6)
    Scenario: Engage direction blinking by car sold in the UK
      When direction blinking is engaged on the blinking side by a car sold in the UK
      Then the daytime running light will not be dimmed for reasons related to where the car was sold

    Rule: When pitman arm is engaged in the same direction, the 3 flashing cycles must first finish before new command is processed

    @requirement(ELS-7)
    Scenario: Engage the pitman arm in an upward direction blinking position during the 3 flashing cycles where the pitman arm was already put in an upward position

      When the pitman arm is moved in an upward direction blinking position during the 3 flashing cycles where the pitman arm was in an upward position
      Then the 3 flashing cycles must finish before the right indicators start the direction blinking

    @requirement(ELS-7)
    Scenario: Engage the pitman arm in a downward tip-blinking position during the 3 flashing cycles where the pitman arm was already put in a downward position
      When the pitman arm is moved in a downward tip-blinking position during the 3 flashing cycles where the pitman arm was in a downward position
      Then the 3 flashing cycles must finish before the left indicators start the new cycle of tip-blinking










