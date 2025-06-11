package by.northdakota.productservice.Event.ProductEventImpl;

import by.northdakota.productservice.Event.ProductEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductEvent implements ProductEvent {
    private Long id;
}
