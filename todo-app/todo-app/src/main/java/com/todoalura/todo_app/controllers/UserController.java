package com.todoalura.todo_app.controllers;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto);
        return ResponseEntity.ok(convertToDto(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto, Authentication auth) {
        // Asegúrate de que el usuario autenticado solo pueda actualizar su propio perfil
        if (!auth.getName().equals(userService.findByUsername(auth.getName()).getUsername())) {
            return ResponseEntity.forbidden().build();
        }
        User updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(convertToDto(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, Authentication auth) {
        // Asegúrate de que el usuario autenticado solo pueda eliminar su propia cuenta
        if (!auth.getName().equals(userService.findByUsername(auth.getName()).getUsername())) {
            return ResponseEntity.forbidden().build();
        }
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<Void> uploadProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile file, Authentication auth) throws IOException {
        // Asegúrate de que el usuario autenticado solo pueda subir su propia foto de perfil
        if (!auth.getName().equals(userService.findByUsername(auth.getName()).getUsername())) {
            return ResponseEntity.forbidden().build();
        }
        userService.updateProfilePicture(userId, file);
        return ResponseEntity.ok().build();
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        // No incluimos la contraseña en el DTO por razones de seguridad
        return dto;
    }
}