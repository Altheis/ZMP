package MSIWPG;

class Factory {
    static View createView(String name) throws IllegalArgumentException {
        switch (name) {
            case "MSIWPG.ConsoleView":
                return new ConsoleView();

            case "MSIWPG.GuiView":
                return new GuiView();

            default:
                throw new IllegalArgumentException();
        }
    }

    static AI createAI(String name) throws IllegalArgumentException {
        switch (name) {
            case "MSIWPG.RandomAI":
                return new RandomAI();
            case "MSIWPG.ScoringAI":
                return new ScoringAI();
            default:
                throw new IllegalArgumentException();
        }
    }
}
