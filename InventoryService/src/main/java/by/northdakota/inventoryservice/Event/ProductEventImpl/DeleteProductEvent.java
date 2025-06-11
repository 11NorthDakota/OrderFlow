package by.northdakota.inventoryservice.Event.ProductEventImpl;

import by.northdakota.inventoryservice.Event.ProductEvent;
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
