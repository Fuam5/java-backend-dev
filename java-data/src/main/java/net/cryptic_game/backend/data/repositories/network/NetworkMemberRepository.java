package net.cryptic_game.backend.data.repositories.network;

import net.cryptic_game.backend.data.entities.device.Device;
import net.cryptic_game.backend.data.entities.network.Network;
import net.cryptic_game.backend.data.entities.network.NetworkMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NetworkMemberRepository extends JpaRepository<NetworkMember, UUID> {

    Optional<NetworkMember> findByKeyDeviceAndKeyNetwork(Device device, Network network);

    List<NetworkMember> findAllByKeyDevice(Device device);

    List<NetworkMember> findAllByKeyNetwork(Network network);
}
