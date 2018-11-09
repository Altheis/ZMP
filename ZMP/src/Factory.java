public class Factory {
    static View createView(String name) throws IllegalArgumentException {
        switch (name) {
            case "ConsoleView":
                return new ConsoleView();

            case "GuiView":
                return new GuiView();

            default:
                throw new IllegalArgumentException();
        }
    }

    static AI createAI(String name) throws IllegalArgumentException {
        switch (name) {
            case "ConsoleView":
                return new RandomAI();
            case "ScoringAI":
                return new ScoringAI();
            default:
                throw new IllegalArgumentException();
        }
    }
}
