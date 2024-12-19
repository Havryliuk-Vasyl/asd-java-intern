package team.asd.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.asd.entity.Party;
import team.asd.service.PartyService;

@RestController
@RequestMapping("/api/party")
public class PartyController {

	private final PartyService partyService;

	public PartyController(PartyService partyService) {
		this.partyService = partyService;
	}

	@GetMapping("/party")
	public Party getPartyById(@RequestParam("id") int id) {
		return partyService.readById(id);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createParty(@RequestBody Party party) {
		try {
			partyService.create(party);
			return ResponseEntity.ok("Party created successfully!");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().body("Error creating party: " + e.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateParty(@RequestBody Party party) {
		try {
			partyService.update(party);
			return ResponseEntity.ok("Party updated successfully!");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().body("Error updating party: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteParty(@RequestParam("id") int id) {
		try {
			partyService.delete(id);
			return ResponseEntity.ok("Party deleted successfully!");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().body("Error deleting party: " + e.getMessage());
		}
	}
}