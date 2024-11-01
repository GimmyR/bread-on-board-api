package mg.breadOnBoard.service;

import java.util.Optional;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mg.breadOnBoard.model.Session;
import mg.breadOnBoard.repository.SessionRepository;

@Service
public class SessionService {
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public String save(String accountId) {
		
		RandomDataGenerator generator = new RandomDataGenerator();
		Session session = new Session();
		session.setAccountId(accountId);
		session.setId(generator.nextHexString(Session.LENGTH_ID));
		Optional<Session> opt = sessionRepository.findById(session.getId());
		
		while(opt.isPresent()) {
			
			session.setId(generator.nextHexString(Session.LENGTH_ID));
			opt = sessionRepository.findById(session.getId());
			
		} sessionRepository.save(session);
		
		return session.getId();
		
	}
	
	public Session findById(String sessionId) throws SessionNotFoundException {
		
		Optional<Session> opt = sessionRepository.findById(sessionId);
		
		if(opt.isEmpty())
			throw new SessionNotFoundException();
		else return opt.get();
		
	}
	
	public void delete(Session session) {
		
		sessionRepository.delete(session);
		
	}

}
