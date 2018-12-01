package ZMP;

public class Factory {
    static View createView(String name) throws IllegalArgumentException {
        switch (name) {
            case "ZMP.ConsoleView":
                return new ConsoleView();

            case "ZMP.GuiView":
                return new GuiView();

            default:
                throw new IllegalArgumentException();
        }
    }

    static AI createAI(String name) throws IllegalArgumentException {
        switch (name) {
            case "ZMP.RandomAI":
                return new RandomAI();
            case "ZMP.ScoringAI":
                return new ScoringAI();
            default:
                throw new IllegalArgumentException();
        }
    }
}
