package repositories;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.TeamGroup;
import utilities.GroupName;

@Repository
public abstract class GroupRepository extends AbstractEntityRepository<TeamGroup, Long> {
	public abstract TeamGroup findByNameEqual(GroupName name);
}
