package ca.fun.simplyspend.data;

import ca.fun.simplyspend.exception.SimplySpendCoreException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class SimpleSpendCoreResponse<T, E> {

    private int statusCode;

    private Optional<T> view;

    private Optional<E> errorMessage;


    boolean check() {
        if (this.errorMessage.isPresent() && this.view.isPresent()) {
            throw new SimplySpendCoreException(500, "Both view and error message are present");
        } else if (this.errorMessage.isEmpty() && this.view.isEmpty()) {
            throw new SimplySpendCoreException(500, "Both view and error message are absent");
        }
        return true;
    }


}
