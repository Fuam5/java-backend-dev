package net.cryptic_game.backend.data.service;

import com.google.gson.JsonObject;
import lombok.Data;
import net.cryptic_game.backend.base.json.JsonBuilder;
import net.cryptic_game.backend.base.json.JsonSerializable;
import net.cryptic_game.backend.base.sql.models.TableModelAutoId;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Entity representing a brute force service entry in the database.
 *
 * @since 0.3.0
 */
@Entity
@Table(name = "service_brute_force")
@Data
public final class ServiceBruteForce extends TableModelAutoId implements JsonSerializable {

    @Column(name = "started", updatable = false, nullable = false)
    private int started;

    @ManyToOne
    @JoinColumn(name = "target_service_id", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private Service targetService;

    @Column(name = "progress", updatable = true, nullable = false)
    private float progress;

    /**
     * Generates a {@link JsonObject} containing all relevant {@link ServiceBruteForce} information.
     *
     * @return The generated {@link JsonObject}
     */
    @Override
    public JsonObject serialize() {
        return JsonBuilder.create("id", this.getId())
                .add("started", this.getStarted())
                .add("target_service_id", this.getTargetService().getId())
                .add("progress", this.getProgress())
                .build();
    }
}
