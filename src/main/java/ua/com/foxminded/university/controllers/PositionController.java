package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Position;
import ua.com.foxminded.university.service.PositionService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/position")
public class PositionController {
    private final PositionService positionService;

    @GetMapping()
    public String getAll(Model model) {
        log.info("getAll() called");
        List<Position> result = positionService.findAll();
        model.addAttribute("positions", result);
        log.info("Exit: {}", result);
        return "position/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        log.info("Enter: getById('{}')", id);
        Position result = positionService.findById(id);
        model.addAttribute("position", result);
        log.info("Exit: {}, {}", result);
        return "position/showOnePosition";
    }


    @GetMapping("/new")
    public String getPageCreatePosition(Model model) {
        log.info("getPageCreatePosition() called");
        model.addAttribute("position", new Position());
        return "position/new";
    }

    @PostMapping
    public String createPosition(@ModelAttribute("position") Position position) {
        log.info("Enter: createFaculty('{}')", position);
        Position result = positionService.save(position);
        log.info("Exit: {}", result);
        return "redirect:/position/" + result.getId();
    }

    @GetMapping("/{id}/edit")
    public String getPageEdit(@PathVariable("id") int id, Model model) {
        log.info("Enter: getPageEdit('{}')", id);
        Position result = positionService.findById(id);
        model.addAttribute("position", result);
        log.info("Exit: {}", result);
        return "position/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("position") Position position, @PathVariable("id") int id) {
        log.info("Enter: update('{}', '{}')", position, id);
        position.setId(id);
        positionService.update(position);
        log.info("Exit: {}", position);
        return "redirect:/position/" + position.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        log.info("Enter: delete('{}')", id);
        positionService.deleteById(id);
        log.info("delete('{}') was success", id);
        return "redirect:/position/";
    }
}
