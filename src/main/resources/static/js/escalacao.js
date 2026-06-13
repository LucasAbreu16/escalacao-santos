function parseFormacao(formacao) {
  if (formacao === "4-4-2") return { GOLEIRO: 1, LATERAL: 2, ZAGUEIRO: 2, MEIO_CAMPISTA: 4, ATACANTE: 2, TOTAL: 11 };
  if (formacao === "4-3-3") return { GOLEIRO: 1, LATERAL: 2, ZAGUEIRO: 2, MEIO_CAMPISTA: 3, ATACANTE: 3, TOTAL: 11 };
  if (formacao === "4-5-1") return { GOLEIRO: 1, LATERAL: 2, ZAGUEIRO: 2, MEIO_CAMPISTA: 5, ATACANTE: 1, TOTAL: 11 };
  return { GOLEIRO: 1, LATERAL: 2, ZAGUEIRO: 2, MEIO_CAMPISTA: 4, ATACANTE: 2, TOTAL: 11 };
}

function spreadX(count, y) {
  if (count <= 0) return [];
  if (count === 1) return [{ x: 50, y }];
  const slots = [];
  const margin = 14;
  const width = 100 - margin * 2;
  for (let i = 0; i < count; i++) {
    slots.push({ x: margin + (width * i) / (count - 1), y });
  }
  return slots;
}

function buildSlotLayout(formacao) {
  const limits = parseFormacao(formacao);
  const slots = [];

  spreadX(limits.ATACANTE, 12).forEach((p) =>
    slots.push({ ...p, role: "ATA", pos: "ATACANTE" })
  );

  if (formacao === "4-5-1") {
    spreadX(3, 32).forEach((p) => slots.push({ ...p, role: "MEI", pos: "MEIO_CAMPISTA" }));
    spreadX(2, 48).forEach((p) => slots.push({ ...p, role: "MEI", pos: "MEIO_CAMPISTA" }));
  } else {
    spreadX(limits.MEIO_CAMPISTA, formacao === "4-3-3" ? 38 : 42).forEach((p) =>
      slots.push({ ...p, role: "MEI", pos: "MEIO_CAMPISTA" })
    );
  }

  slots.push({ x: 18, y: 68, role: "LAT", pos: "LATERAL" });
  slots.push({ x: 38, y: 70, role: "ZAG", pos: "ZAGUEIRO" });
  slots.push({ x: 62, y: 70, role: "ZAG", pos: "ZAGUEIRO" });
  slots.push({ x: 82, y: 68, role: "LAT", pos: "LATERAL" });

  slots.push({ x: 50, y: 88, role: "GOL", pos: "GOLEIRO" });

  return slots;
}

function pickPlayersForSlot(players, pos, usedIds) {
  const available = players.filter((p) => p.posicao === pos && !usedIds.has(p.id));
  available.sort((a, b) => a.nome.localeCompare(b.nome));
  if (available.length === 0) return null;
  usedIds.add(available[0].id);
  return available[0];
}

function assignPlayersToSlots(slots, checked) {
  const usedIds = new Set();
  return slots.map((slot) => ({
    ...slot,
    player: pickPlayersForSlot(checked, slot.pos, usedIds),
  }));
}

function displayName(nome) {
  if (!nome) return "";
  return nome.trim();
}

function getPlayerInfo(inputEl) {
  const row = inputEl.closest(".player-row");
  const nameEl = row ? row.querySelector(".player-name") : null;
  return {
    id: inputEl.value,
    posicao: inputEl.getAttribute("data-posicao"),
    nome: nameEl ? nameEl.textContent.trim() : "",
  };
}

function countSelectedByPosicao() {
  const checked = document.querySelectorAll('input[name="jogadorIds"]:checked');
  const counts = { GOLEIRO: 0, LATERAL: 0, ZAGUEIRO: 0, MEIO_CAMPISTA: 0, ATACANTE: 0, TOTAL: 0 };
  checked.forEach((el) => {
    const pos = el.getAttribute("data-posicao");
    if (counts[pos] !== undefined) counts[pos] += 1;
    counts.TOTAL += 1;
  });
  return counts;
}

function renderPitch(assignedSlots) {
  const container = document.getElementById("pitch-slots");
  if (!container) return;
  container.innerHTML = "";

  assignedSlots.slice().reverse().forEach((slot) => {
    const div = document.createElement("div");
    div.className = "pitch-slot" + (slot.player ? " filled" : " empty");
    div.style.left = slot.x + "%";
    div.style.top = slot.y + "%";

    const circle = document.createElement("div");
    circle.className = "pitch-slot-circle";
    circle.textContent = slot.role;
    div.appendChild(circle);

    const name = document.createElement("div");
    name.className = "pitch-slot-name";
    name.textContent = slot.player ? displayName(slot.player.nome) : "—";
    div.appendChild(name);

    container.appendChild(div);
  });
}

function renderLineupList(assignedSlots) {
  const container = document.getElementById("lineup-list");
  if (!container) return;
  container.innerHTML = "";

  const order = [...assignedSlots].reverse();
  order.forEach((slot) => {
    const row = document.createElement("div");
    row.className = "lineup-row" + (slot.player ? "" : " empty");

    const role = document.createElement("span");
    role.className = "lineup-role";
    role.textContent = slot.role;
    row.appendChild(role);

    const dash = document.createElement("span");
    dash.className = "lineup-dash";
    dash.textContent = "—";
    row.appendChild(dash);

    const name = document.createElement("span");
    name.className = "lineup-name";
    name.textContent = slot.player ? slot.player.nome : "";
    row.appendChild(name);

    container.appendChild(row);
  });
}

function updateUI() {
  const formacaoSelect = document.querySelector('select[name="formacao"]');
  const formacao = formacaoSelect ? formacaoSelect.value : "4-4-2";
  const limits = parseFormacao(formacao);
  const counts = countSelectedByPosicao();

  const totalEl = document.getElementById("count-total");
  if (totalEl) totalEl.textContent = counts.TOTAL + "/" + limits.TOTAL;

  ["GOLEIRO", "LATERAL", "ZAGUEIRO", "MEIO_CAMPISTA", "ATACANTE"].forEach((p) => {
    const el = document.getElementById("count-" + p);
    if (el) el.textContent = counts[p] + "/" + limits[p];
  });

  const checked = Array.from(document.querySelectorAll('input[name="jogadorIds"]:checked')).map(getPlayerInfo);
  const slots = buildSlotLayout(formacao);
  const assigned = assignPlayersToSlots(slots, checked);

  renderPitch(assigned);
  renderLineupList(assigned);

  document.querySelectorAll('input[name="jogadorIds"]').forEach((el) => {
    const pos = el.getAttribute("data-posicao");
    const isChecked = el.checked;
    const reachedPos = counts[pos] >= limits[pos];
    const reachedTotal = counts.TOTAL >= limits.TOTAL;
    el.disabled = !isChecked && (reachedPos || reachedTotal);
  });
}

document.addEventListener("DOMContentLoaded", () => {
  document.addEventListener("change", (e) => {
    if (e.target && (e.target.matches('input[name="jogadorIds"]') || e.target.matches('select[name="formacao"]'))) {
      updateUI();
    }
  });

  const form = document.querySelector('form[method="post"]');
  if (form) {
    form.addEventListener("submit", () => {
      document.querySelectorAll('input[name="jogadorIds"]').forEach((el) => {
        el.disabled = false;
      });
    });
  }

  updateUI();
});
