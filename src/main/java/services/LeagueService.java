package services;


import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import entities.League;
import entities.Season;
import entities.TeamGroup;
import entities.Week;
import repositories.LeagueRepository;
import repositories.SeasonRepository;
import webcontrollers.LeagueController;

@Stateless
@LocalBean
public class LeagueService {
	private final Logger LOGGER = Logger.getLogger(LeagueService.class.getName());
	
	@Inject
	private LeagueRepository leagueRepo;
	@Inject
	private SeasonRepository seasonRepo;
	
	public void generateSeasonForLeague(String leagueName){
		LOGGER.info("league service was called");
		League league = leagueRepo.findByNameEqual(leagueName);		
		LOGGER.info("league found:" + league.getName());
		Season newSeason = new Season();
		if(league.getSeasons().isEmpty()){
			newSeason.setSeason("" + LocalDate.now().getYear());
		}
		else{
			newSeason.setSeason("" + (Integer.parseInt(league.getSeasons()
										 .get(league.getSeasons().size() - 1).getSeason())+1));
		}
		newSeason.setWeeks(
				Stream.generate(Week::new)
				.limit(3)
				.collect(Collectors.toList()));
		int i=1;
		for(Week week : newSeason.getWeeks()){
			week.setSeason(newSeason);
			week.setWeekCount(i++);
		}
		league.getSeasons().add(seasonRepo.save(newSeason));
		leagueRepo.save(league);
		
	}
	
	public int getNumberOfTeams(League league){
		int sum = 0;
		for(TeamGroup group : league.getGroups()){
			sum += group.getTeams().size();
		}
		return sum;
	}
}
