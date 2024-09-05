package uwu.narumi.deobfuscator.api.asm.matcher.rule.group;

import org.objectweb.asm.tree.AbstractInsnNode;
import uwu.narumi.deobfuscator.api.asm.matcher.rule.Match;
import uwu.narumi.deobfuscator.api.asm.matcher.rule.MatchContext;

public class PositionedMatch extends Match {

  private final int offset;
  private final boolean previous;
  private final boolean skipAsmInstructions;
  private final Match match;

  private PositionedMatch(int offset, boolean skipAsmInstructions, Match match) {
    this.offset = Math.abs(offset);
    this.previous = offset < 0;
    this.skipAsmInstructions = skipAsmInstructions;
    this.match = match;
  }

  @Override
  protected boolean test(MatchContext context) {
    return match.matches(context.ofInsn(
        walk(context.insn())
    ));
  }

  private AbstractInsnNode walk(AbstractInsnNode node) {
    if (skipAsmInstructions) {
      node = previous ? node.previous(offset) : node.getPrevious(offset);
    } else {
      node = previous ? node.next(offset) : node.getNext(offset);
    }

    return node;
  }
}
