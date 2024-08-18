
@RequestMapping("/api/users")
class UserController(
    @Autowired private val userRepository: UserRepository
) {

    @GetMapping
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    @PostMapping
    fun createUser(@RequestBody user: User): User {
        return userRepository.save(user)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: User): User {
        val existingUser = userRepository.findById(id).orElseThrow()
        existingUser.username = user.username
        existingUser.password = user.password
        existingUser.email = user.email
        return userRepository.save(existingUser)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userRepository.deleteById(id)
    }
}