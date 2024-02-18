package notebook.model.repository.impl;

import notebook.model.dao.impl.FileOperation;
import notebook.util.UserValidator;
import notebook.util.mapper.impl.UserMapper;
import notebook.model.User;
import notebook.model.repository.GBRepository;

import java.util.*;

public class UserRepository implements GBRepository {
    private final UserMapper mapper;
    private final FileOperation operation;

    public UserRepository(FileOperation operation) {
        this.mapper = new UserMapper();
        this.operation = operation;
    }

    @Override
    public List<User> findAll() {
        List<String> lines = operation.readAll();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.toOutput(line));
        }
        return users;
    }

    @Override
    public User create(User user) {
        UserValidator userValidator = new UserValidator();
        user = userValidator.validate(user);
        List<User> users = findAll();

        long max = 0L;
        for (User u : users) {
            long id = u.getId();
            if (max < id) {
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        write(users);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> update(Long userId, User update) {

        List<User> users = findAll();
//        User editUser = users.stream()
//                .filter(u -> u.getId()
//                        .equals(userId))
//                .findFirst().orElseThrow(() -> new RuntimeException("User not found"));
        User editUser = null;
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                editUser = user;
            }
        }

        if (update.getFirstName().isEmpty()) {
            editUser.setFirstName(editUser.getFirstName());

        } else editUser.setFirstName(update.getFirstName());

        if (update.getLastName().isEmpty()) {
            editUser.setLastName(editUser.getFirstName());

        } else editUser.setLastName(update.getLastName());

        if (update.getPhone().isEmpty()) {
            editUser.setPhone(editUser.getPhone());
        } else editUser.setPhone(update.getPhone());

        write(users);
        return Optional.of(update);
    }

//    @Override
//    public boolean delete(Long id) {
//        List<User>users = findAll();
//        for (User user: users){
//            if(Objects.equals(user.getId(),id)){
//                users.removeAll(Collection.);
//            }
//        }
//        return false;
//    }
@Override
public boolean delete(Long id) {
    List<User> users = findAll();
    for (User user : users) {
        if (Objects.equals(user.getId(), id)) {
            users.removeAll(Collections.singleton(user));
            System.out.println("удаление пользователя... ");
            List<String> lines = new ArrayList<>();
            for (User u : users) {
                lines.add(mapper.toInput(u));
            }
            operation.saveAll(lines);
            break;
        }

    }
    return false;
}

    private void write(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u : users) {
            lines.add(mapper.toInput(u));
        }
        operation.saveAll(lines);
    }

}
