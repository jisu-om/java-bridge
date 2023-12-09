package bridge.controller;

import bridge.view.InputView;
import bridge.view.OutputView;

import java.util.function.Supplier;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;

    private GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public static GameController create() {
        return new GameController(InputView.getInstance(), OutputView.getInstance());
    }

    public void start() {
        outputView.printStart();
    }

    private VisitingDate createVisitingDate() {
        return readUserInput(() -> {
            int input = inputView.readVisitingDate();
            return VisitingDate.from(input);
        });
    }

    private Orders createOrders() {
        return readUserInput(() -> {
            List<OrderItem> items = inputView.readOrders().stream()
                    .map(OrderItemDto::toOrderItem)
                    .toList();
            return Orders.from(items);
        });
    }

    private <T> T readUserInput(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
