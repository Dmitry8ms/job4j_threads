package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public final class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.put(account.id(), account) != null;
    }

    public synchronized boolean update(Account account) {
        boolean result = false;
        if (accounts.containsKey(account.id())) {
            result = add(account);
        }
        return result;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> result = Optional.empty();
        if (accounts.containsKey(id)) {
            result = Optional.of(accounts.get(id));
        }
        return result;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (getById(fromId).orElse(new Account(0, 0)).amount() - amount >= 0
                && getById(toId).isPresent()) {
            result = update(new Account(fromId, accounts.get(fromId).amount() - amount))
                && update(new Account(toId, accounts.get(toId).amount() + amount));
        }
        return result;
    }
}
