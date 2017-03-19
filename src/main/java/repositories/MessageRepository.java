package repositories;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.Message;

@Repository
public abstract class MessageRepository extends AbstractEntityRepository<Message, Long>{

}
