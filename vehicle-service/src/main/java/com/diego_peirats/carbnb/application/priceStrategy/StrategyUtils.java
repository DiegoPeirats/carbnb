package com.diego_peirats.carbnb.application.priceStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.diego_peirats.carbnb.domain.entity.Vehicle;
import com.diego_peirats.carbnb.infrastructure.repository.VehicleRepository;

import DTOs.TransactionDto;
import lombok.RequiredArgsConstructor;
import vehicle.StatusType;

@Service
@RequiredArgsConstructor
public class StrategyUtils {

	private final VehicleRepository repository;

	public Vehicle getVehicle(Long id) {
		Optional<Vehicle> optional = repository.findById(id);
		
		if (optional.isEmpty()) return null;
		
		return optional.get();
	}
	
	public static boolean isHolidays(LocalDate start, LocalDate end) {
		Set<LocalDate> holidays = Set.of(
		        LocalDate.of(2025, 1, 1),   // Año Nuevo
		        LocalDate.of(2025, 1, 6),   // Reyes
		        LocalDate.of(2025, 4, 18),  // Viernes Santo
		        LocalDate.of(2025, 5, 1),   // Día del Trabajador
		        LocalDate.of(2025, 8, 15),  // Asunción
		        LocalDate.of(2025, 10, 12), // Fiesta Nacional
		        LocalDate.of(2025, 11, 1),  // Todos los Santos
		        LocalDate.of(2025, 12, 6),  // Constitución
		        LocalDate.of(2025, 12, 8),  // Inmaculada Concepción
		        LocalDate.of(2025, 12, 25)  // Navidad
		    );
		 if (start == null || end == null || end.isBefore(start)) {
	            throw new IllegalArgumentException("Fechas inválidas");
	        }

        return holidays.stream().anyMatch(date -> 
            (!date.isBefore(start) && !date.isAfter(end))
        );
	}
	
	public static boolean isInNext7Days(LocalDate date) {
        if (date == null) return false;

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysLater = today.plusDays(7);

        return !date.isBefore(today) && !date.isAfter(sevenDaysLater);
    }
	
	public boolean isHighDemand() {
		
		int totalVehicles = repository.findAll().size();
		int rentedVehicles = repository.findByStatus(StatusType.RENTED).size();
		
		return rentedVehicles / totalVehicles >= 0.8;
	}
	
	public static boolean isVipClient(List<TransactionDto> transactions) {
	    LocalDateTime now = LocalDateTime.now();
	    LocalDateTime oneMonthAgo = now.minusDays(30);

	    long rentedInLastMonth = transactions.stream()
	        .map(TransactionDto::getCreatedAt)
	        .filter(dateTime -> dateTime != null &&
	                            dateTime.isAfter(oneMonthAgo) &&
	                            dateTime.isBefore(now))
	        .count();

	    return rentedInLastMonth >= 3; 
	}

}
