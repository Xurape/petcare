package com.petcare.petcare.Utils;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Launcher;
import com.petcare.petcare.Services.Service;
import com.petcare.petcare.Services.ServiceType;
import com.petcare.petcare.Users.*;

public class Seeder {
    private static final boolean isUsersSeeadble = Storage.getStorage().getServices().isEmpty();
    private static final boolean isServicesSeedable = Storage.getStorage().getAdmins().isEmpty() ||
                                                Storage.getStorage().getClients().isEmpty() ||
                                                Storage.getStorage().getCompanies().isEmpty() ||
                                                Storage.getStorage().getEmployees().isEmpty();

    private static final boolean isSeeded = !isUsersSeeadble && !isServicesSeedable;

    /**
     *
     * Check if the storage is seeded
     *
     * @return True if seeded, false if not
     *
     */
    private static boolean isSeeded() {
        return isSeeded;
    }

    /**
     *
     * Seed the database with the default users and services
     *
     * @see #seedUsers() {@link #seedServices()}
     *
     */
    public static void seed() {
        if(isSeeded()) {
            Debug.warning("Storage already seeded! Skipping step...", true, true);
            return;
        }

        Debug.warning("Seeding storage...", true, true);

        if(isUsersSeeadble) {
            seedUsers();
            Debug.success("Users seeded!", true, true);
        }

        if (isServicesSeedable) {
            seedServices();
            Debug.success("Services seeded!", true, true);
        }

        Debug.success("Seeding complete...", true, true);

        try {
            Debug.warning("Serializing data into storage...", true, true);
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
        } catch (CouldNotSerializeException e) {
            Debug.print("Could not serialize storage! " + e.getMessage(), true, true);
            return;
        } finally {
            Debug.success("Data serialized successfully!", true, true);
        }
    }

    /**
     *
     * Seed the storage with the default users
     *
     */
    public static void seedUsers() {
        Admin admin = new Admin("admin", "admin");

        Client client = new Client("client", "client");
        client.setName("Cliente");
        client.setAddress("Rua do Cliente");
        client.setNIF("123456789");
        client.setEmail("cliente@gmail.com");

        Company company = new Company("company", "company");
        company.setName("Empresa");
        company.setAddress("Rua da Empresa");
        company.setNIF("987654321");
        company.setEmail("empresa@gmail.com");

        Employee employee = new Employee("employee", "employee");
        employee.setName("Funcionário");
        employee.setAddress("Rua do Funcionário");
        employee.setNIF("123456789");
        employee.setEmail("employee@gmail.com");

        Storage.getStorage().getAdmins().put(admin.getNIF(), admin);
        Storage.getStorage().getClients().put(client.getNIF(), client);
        Storage.getStorage().getCompanies().put(company.getNIF(), company);
        Storage.getStorage().getEmployees().put(employee.getNIF(), employee);
    }

    /**
     *
     * Seed the storage with the default services
     *
     */
    public static void seedServices() {
        Service grooming = new Service("Tosquia", "Marque uma tosquia para o seu animal!", ServiceType.GROOMING, 15.00);
        Service bathing = new Service("Banho", "Marque um banho para o seu animal!", ServiceType.BATHING, 10.00);
        Service veterinary = new Service("Veterinário", "Marque uma consulta para o seu animal!", ServiceType.VETERINARY, 20.00);
        Service hotel = new Service("Hotel", "Marque uma estadia para o seu animal!", ServiceType.HOTEL, 25.00);
        Service walking = new Service("Passeio", "Marque um passeio para o seu animal!", ServiceType.WALKING, 10.00);
        Service training = new Service("Treino", "Marque um treino para o seu animal!", ServiceType.TRAINING, 15.00);
        Service sitting = new Service("Babysitting", "Marque um serviço de babysitting para o seu animal!", ServiceType.SITTING, 25.00);
        Service daycare = new Service("Creche", "Marque um serviço de creche para o seu animal!", ServiceType.DAYCARE, 45.00);
        Service spa = new Service("Spa", "Marque um serviço de spa para o seu animal!", ServiceType.SPA, 25.00);
        Service transport = new Service("Transporte", "Marque um serviço de transporte para o seu animal!", ServiceType.TRANSPORT, 7.50);

        Storage.getStorage().getServices().add(grooming);
        Storage.getStorage().getServices().add(bathing);
        Storage.getStorage().getServices().add(veterinary);
        Storage.getStorage().getServices().add(hotel);
        Storage.getStorage().getServices().add(walking);
        Storage.getStorage().getServices().add(training);
        Storage.getStorage().getServices().add(sitting);
        Storage.getStorage().getServices().add(daycare);
        Storage.getStorage().getServices().add(spa);
        Storage.getStorage().getServices().add(transport);
    }
}
