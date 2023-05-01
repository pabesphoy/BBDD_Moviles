package com.example.myapplication.repositories;
import com.example.myapplication.model.JoinRequest;
import com.example.myapplication.model.Membership;
import com.example.myapplication.model.Player;
import java.util.Collection;
import java.util.stream.Collectors;
import io.realm.Realm;

public class PlayerRepository implements BaseRepository<Player, String> {

    private JoinRequestRepository joinRequestRepository = new JoinRequestRepository();
    private MembershipRepository membershipRepository = new MembershipRepository();


    @Override
    public Collection<Player> getAll() {
        return realm.where(Player.class).findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Player getByPrimaryKey(String email) {
        return realm.where(Player.class).equalTo("user.email", email).findFirst();
    }

    @Override
    public boolean insertOrUpdate(Player item) {
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();

        return getByPrimaryKey(item.getUser().getEmail()) != null;
    }

    @Override
    public boolean delete(Player item) {
        return deleteByPrimaryKey(item.getUser().getEmail());
    }

    @Override
    public boolean deleteByPrimaryKey(String email) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Player player = getByPrimaryKey(email);
                if(player != null){
                    for (Membership m: realm.where(Membership.class).equalTo("player.user.email", email).findAll()){
                        membershipRepository.delete(m);
                    }
                    for (JoinRequest j: realm.where(JoinRequest.class).equalTo("player.user.email", email).findAll()){
                        joinRequestRepository.delete(j);
                    }
                    player.deleteFromRealm();
                    player.getUser().deleteFromRealm();
                }

            }
        });
        return getByPrimaryKey(email) == null;
    }
}
