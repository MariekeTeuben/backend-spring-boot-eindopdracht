package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.RegistrationCardDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.RegistrationCard;
import nl.novi.backendgarageservice.repository.RegistrationCardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationCardService {


    private final RegistrationCardRepository registrationCardRepos;

    public RegistrationCardService(RegistrationCardRepository registrationCardRepos) {
        this.registrationCardRepos = registrationCardRepos;
    }


    public RegistrationCardDto getRegistrationCardByChassisNumber(String chassisNumber) {
        RegistrationCard registrationCard = registrationCardRepos.findById(chassisNumber).orElseThrow(() -> new ResourceNotFoundException("Registration card cannot be found"));

        RegistrationCardDto registrationCardDto = new RegistrationCardDto();
        registrationCardDto.chassisNumber = registrationCard.getChassisNumber();
        registrationCardDto.name = registrationCard.getName();
        registrationCardDto.postalCode = registrationCard.getPostalCode();

        return registrationCardDto;
    }

    public List<RegistrationCardDto> getAllRegistrationCards() {
        ArrayList<RegistrationCardDto> registrationCardDtoList = new ArrayList<>();
        Iterable<RegistrationCard> allRegistrationCards = registrationCardRepos.findAll();
        for (RegistrationCard registrationCard: allRegistrationCards) {
            RegistrationCardDto registrationCardDto = new RegistrationCardDto();

            registrationCardDto.chassisNumber = registrationCard.getChassisNumber();
            registrationCardDto.name = registrationCard.getName();
            registrationCardDto.postalCode = registrationCard.getPostalCode();

            registrationCardDtoList.add(registrationCardDto);
        }

        if (registrationCardDtoList.size() == 0) {
            throw new ResourceNotFoundException("Registration cards cannot be found");
        }

        return registrationCardDtoList;

    }

    public String createRegistrationCard(RegistrationCardDto registrationCardDto) {
        RegistrationCard registrationCard = new RegistrationCard();

        registrationCard.setChassisNumber(registrationCardDto.chassisNumber);
        registrationCard.setName(registrationCardDto.name);
        registrationCard.setPostalCode(registrationCardDto.postalCode);

        registrationCardRepos.save(registrationCard);

        registrationCardDto.chassisNumber = registrationCard.getChassisNumber();

        return registrationCard.getChassisNumber();
    }

    public Object updateRegistrationCard(String chassisNumber, RegistrationCardDto registrationCardDto) {
        Optional<RegistrationCard> registrationCard = registrationCardRepos.findById(chassisNumber);
        if (registrationCard.isEmpty()) {
            throw new ResourceNotFoundException("No registration card with chassis number:" + chassisNumber);
        } else {
            RegistrationCard updatedRegistrationCard = registrationCard.get();
            updatedRegistrationCard.setChassisNumber(registrationCardDto.chassisNumber);
            updatedRegistrationCard.setName(registrationCardDto.name);
            updatedRegistrationCard.setPostalCode(registrationCardDto.postalCode);

            registrationCardRepos.save(updatedRegistrationCard);

            return updatedRegistrationCard;
        }
    }

    public Object deleteRegistrationCard(String chassisNumber)  {
        Optional<RegistrationCard> optionalRegistrationCard = registrationCardRepos.findByChassisNumber(chassisNumber);
        if (optionalRegistrationCard.isEmpty()) {
            throw new ResourceNotFoundException("Registration card cannot be found");
        } else {
            RegistrationCard registrationCard = optionalRegistrationCard.get();
            registrationCardRepos.delete(registrationCard);
            return "Registration card deleted successfully";
        }
    }
}
