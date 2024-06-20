package portfolioserver.core.equity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolioserver.common.BaseController;
import portfolioserver.core.geography.Geography;
import portfolioserver.core.geography.GeographyExposure;
import portfolioserver.core.sector.SectorExposure;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class EquityController extends BaseController {

    private final EquityRepository equityRepository;

    @Autowired
    public EquityController(EquityRepository equityRepository) {
        super(); // Enables access to logger.
        this.equityRepository = equityRepository;
    }

    @GetMapping(value = "/equities/get")
    public List<Equity> get() {
        return equityRepository.findAll();
    }

    @GetMapping(value = "/equity/{id}/get")
    public ResponseEntity<Equity> getOne(@PathVariable int id) {
        return ResponseEntity.ok(
            equityRepository.findById((long) id)
                .orElseThrow(() -> new NoSuchElementException(Equity.class.getSimpleName()))
        );
    }

    /** Adds a portfolio as a list of equities. */
    @PostMapping(value = "/equities/add")
    public ResponseEntity<Void> addAll(@RequestBody List<Equity> equityList) {
        equityRepository.saveAll(equityList);

        return ResponseEntity.ok().build();
    }

    /** Updates existing equities from an imported portfolio. */
    @PutMapping(value = "/equities/update")
    public ResponseEntity<Void> updateAll(@RequestBody List<Equity> equityList) {

        equityList.forEach(equity -> {
            equityRepository.findById(equity.getId())
                .ifPresentOrElse(
                    equityRepository::save,
                    () -> { throw new NoSuchElementException(Equity.class.getSimpleName()); }
                );
        });
        return ResponseEntity.ok().build();
    }

    /** Each call overwrite the whole set of exposures. */
    @PostMapping(value = "/equity/{id}/exposures/geo/add")
    public ResponseEntity<Void> addGeoExposures(@PathVariable int id, @RequestBody List<GeographyExposure> exposures) {

        equityRepository.findById((long) id)
            .map((equity) -> {
                equity.setGeographyExposures(exposures);
                return equityRepository.save(equity);
            })
            .orElseThrow(() -> new NoSuchElementException(Geography.class.getSimpleName()));

        return ResponseEntity.ok().build();
    }

    /** Each call overwrite the whole set of exposures. */
    @PostMapping(value = "/equity/{id}/exposures/sec/add")
    public ResponseEntity<Void> addSectorExposures(@PathVariable int id, @RequestBody List<SectorExposure> exposures) {

        equityRepository.findById((long) id)
            .map((equity) -> {
                equity.setSectorExposures(exposures);
                return equityRepository.save(equity);
            })
            .orElseThrow(() -> new NoSuchElementException(Geography.class.getSimpleName()));

        return ResponseEntity.ok().build();
    }

    /** Deletes a portfolio as a list of equities. */
    @DeleteMapping(value = "/equities/delete")
    public ResponseEntity<Void> deleteAll() {
        equityRepository.deleteAll();

        return ResponseEntity.ok().build();
    }
}
