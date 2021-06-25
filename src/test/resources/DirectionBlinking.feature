Feature: Direction Blinking Indicator

  The pitman arm is used for controlling the direction blinking indicator. This feature is for defining the positions available by the pitman arm for indicating through blinking.

  Rule: Direction blinking is only available when ignition is on.
    Background:
      Given the Ignition is on

    @requirement(ELS-1,ELS-5)
    Scenario Outline: Engage Direction Blinking
      When the pitman arm is moved in the "<arm state>" position
      Then the vehicle flashes all "<direction>" indicators synchronously

      Examples:
        | arm state | direction |
        | Upward    | Right     |
        | Downward  | Left      |
