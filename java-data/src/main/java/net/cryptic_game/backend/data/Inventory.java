package net.cryptic_game.backend.data;

import com.google.gson.JsonObject;
import lombok.Data;
import net.cryptic_game.backend.base.json.JsonBuilder;
import net.cryptic_game.backend.base.json.JsonSerializable;
import net.cryptic_game.backend.base.sql.models.TableModelAutoId;
import net.cryptic_game.backend.data.user.User;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity representing an inventory entry in the database.
 *
 * @since 0.3.0
 */
@Entity
@Table(name = "inventory")
@Data
public final class Inventory extends TableModelAutoId implements JsonSerializable {

    @Column(name = "size", updatable = true, nullable = false)
    private int size;

    @ManyToOne
    @JoinColumn(name = "owner", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private User owner;

    /**
     * Generates a {@link JsonObject} containing all relevant {@link Inventory} information.
     *
     * @return The generated {@link JsonObject}
     */
    @Override
    public JsonObject serialize() {
        return JsonBuilder.create("id", this.getId())
                .add("size", this.getSize())
                .add("owner", this.getOwner().getId())
                .build();
    }
}
