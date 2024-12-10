#ifndef DEMO_ENTERPRISE_H
#define DEMO_ENTERPRISE_H

#include <string>

class Enterprise {
public:
    Enterprise() = default;
    Enterprise(long id, const std::string& name)
        : id(id), name(name) {}

    long getId() const { return id; }
    void setId(long id) { this->id = id; }

    const std::string& getName() const { return name; }
    void setName(const std::string& name) { this->name = name; }

    std::string toString() const {
        return "Enterprise{" +
            "id=" + std::to_string(id) +
            ", name='" + name + '\'' +
            '}';
    }

private:
    long id;
    std::string name;
};

#endif // DEMO_ENTERPRISE_H